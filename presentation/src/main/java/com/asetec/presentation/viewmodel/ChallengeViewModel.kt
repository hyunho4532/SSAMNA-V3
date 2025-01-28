package com.asetec.presentation.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.state.Challenge
import com.asetec.domain.model.state.ChallengeDTO
import com.asetec.domain.usecase.challenge.ChallengeCase
import com.asetec.presentation.ui.util.FormatChildren
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeCase: ChallengeCase,
    @ApplicationContext appContext: Context
): ViewModel() {

    private val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _challenge = MutableStateFlow(ChallengeDTO())
    private val challenge: StateFlow<ChallengeDTO> = _challenge

    private val _challengeData = MutableStateFlow<List<ChallengeDTO>>(emptyList())
    val challengeData: StateFlow<List<ChallengeDTO>> = _challengeData

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveChallenge(data: Challenge) {
        val googleId = sharedPreferences.getString("id", "")

        try {
            _challenge.update {
                it.copy(
                    googleId = googleId!!,
                    title = data.name,
                    todayDate = FormatChildren.todayFormatDate()
                )
            }

            viewModelScope.launch {
                challengeCase.saveChallenge(challenge.value)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun selectChallengeById() {
        val googleId = sharedPreferences.getString("id", "")
        val challengeDTO = challengeCase.selectChallengeFindById(googleId!!)

        _challengeData.value = challengeDTO
    }
}