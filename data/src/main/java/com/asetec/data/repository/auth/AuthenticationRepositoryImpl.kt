package com.asetec.data.repository.auth

import android.util.Log
import com.asetec.domain.model.user.User
import com.asetec.domain.model.user.UserDTO
import com.asetec.domain.repository.user.AuthenticationRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : AuthenticationRepository {

    override fun signInWithGoogle(
        task: Task<GoogleSignInAccount>?,
        onSuccess: (id: String, email: String, name: String) -> Unit
    ): Boolean {

        val account = task?.getResult(ApiException::class.java)

        try {
            account?.let { signInAccount ->
                onSuccess(signInAccount.id.toString(), signInAccount.email.toString(), signInAccount.displayName.toString())
            } ?: run {
                onSuccess("", "", "")
            }
        } catch (e: ApiException) {
            onSuccess("", "", "")
        }

        return true
    }

    override suspend fun saveUser(user: User) {
        val userDTO = UserDTO(
            googleId = user.id,
            email = user.email,
            name = user.name,
            age = user.age.toInt(),
            recentExerciseCheck = user.recentExerciseCheck,
            recentExerciseName = user.recentExerciseName,
            recentWalkingCheck = user.recentWalkingCheck,
            recentWalkingOfWeek = user.recentWalkingOfWeek,
            recentWalkingOfTime = user.recentWalkingOfTime,
            targetPeriod = user.targetPeriod
        )

        postgrest.from("User").insert(userDTO)
    }

    override suspend fun selectUserFindById(googleId: String) : UserDTO {
        return withContext(Dispatchers.IO) {
            postgrest.from("User").select {
                filter {
                    eq("google_id", googleId)
                }
            }.decodeSingle<UserDTO>()
        }
    }
}