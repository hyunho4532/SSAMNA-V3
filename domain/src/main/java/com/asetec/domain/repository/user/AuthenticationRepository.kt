package com.asetec.domain.repository.user

import com.asetec.domain.model.user.User
import com.asetec.domain.model.user.UserDTO
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

interface AuthenticationRepository {
    suspend fun validateIsUser(task: Task<GoogleSignInAccount>?, onSuccess: (isNotUser: Boolean) -> Unit)
    fun signInWithGoogle(task: Task<GoogleSignInAccount>?, onSuccess: (id: String, email: String, name: String) -> Unit)
    suspend fun saveUser(user: User)
    suspend fun selectUserFindById(googleId: String) : UserDTO
}