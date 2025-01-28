package com.asetec.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.user.User
import com.asetec.domain.usecase.user.LoginCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val loginCase: LoginCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _user = MutableStateFlow(User(
        id = getSavedLoginState(),
        recentExerciseName = ""
    ))

    val user: StateFlow<User> = _user

    /**
     * SP에 담은 id 값을 가져온다.
     */
    fun getSavedLoginState(): String {
        return sharedPreferences.getString("id", "")!!
    }

    /**
     * 구글 로그인 진행 후, id 값을 SP에 담는다.
     */
    private fun saveLoginState(id: String) {
        sharedPreferences.edit().putString("id", id).apply()
    }

    fun mergeAuthStateIntoUserState(user: User): Boolean {
        _user.update {
            it.copy(
                id = user.id,
                email = user.email,
                name = user.name
            )
        }

        return true
    }

    fun onGoogleSignIn(task: Task<GoogleSignInAccount>?) {
        viewModelScope.launch {
            loginCase.invoke(task) { id, email, name ->
                saveLoginState(id)

                _user.update {
                    it.copy(
                        id = id,
                        email = email,
                        name = name
                    )
                }
            }
        }
    }

    /**
     * NumberPicker에서 값이 변경될 때마다, 나이를 저장한다.
     */
    fun saveAge(age: Float) {
        _user.update {
            it.copy(age = age)
        }
    }

    fun saveExerciseName(name: String) {
        _user.update {
            it.copy(recentExerciseName = name)
        }
    }

    fun saveWalkingOfWeek(week: String) {
        _user.update {
            it.copy(recentWalkingOfWeek = week)
        }
    }

    fun saveWalkingOfTime(time: String) {
        _user.update {
            it.copy(recentWalkingOfTime = time)
        }
    }

    fun saveChecks(id: Number, text: String) {
        _user.update {
            when (id) {
                0 -> it.copy(recentExerciseCheck = text)
                1 -> it.copy(recentWalkingCheck = text)
                2 -> it.copy(targetPeriod = text)
                else -> throw Exception("에러")
            }
        }
    }

    /** 최종적으로 정보 확인 후 데이터베이스에 저장 **/
    fun saveUser(userState: User) {
        viewModelScope.launch {
            loginCase.saveUser(userState)
        }
    }

    fun selectUserFindById(googleId: String) {
        viewModelScope.launch {
            val user = loginCase.selectUserFindById(googleId)

            _user.update {
                it.copy(
                    googleId,
                    name = user.name,
                    email = user.email,
                    age = user.age.toFloat(),
                    recentWalkingOfTime = user.recentWalkingOfTime,
                    recentWalkingOfWeek = user.recentWalkingOfWeek,
                    recentWalkingCheck = user.recentWalkingCheck,
                    recentExerciseName = user.recentExerciseName,
                    recentExerciseCheck = user.recentExerciseCheck,
                    targetPeriod = user.targetPeriod
                )
            }
        }
    }
}