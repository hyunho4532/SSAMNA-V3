package com.asetec.domain.usecase.user

import android.util.Log
import com.asetec.domain.model.user.User
import com.asetec.domain.model.user.UserDTO
import com.asetec.domain.repository.user.AuthenticationRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class LoginCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend fun invoke(task: Task<GoogleSignInAccount>?, onSuccess: (id: String, email: String, name: String, isNotUser: Boolean) -> Unit) {
       authenticationRepository.validateIsUser(task) { isUser ->
            authenticationRepository.signInWithGoogle(task) { id, email, name ->
                onSuccess(id, email, name, isUser)
            }
       }
    }

    suspend fun saveUser(user: User) {
        authenticationRepository.saveUser(user)
    }

    suspend fun selectUserFindById(googleId: String) : UserDTO {
        return authenticationRepository.selectUserFindById(googleId)
    }
}