package com.asetec.data.modules

import com.asetec.data.repository.auth.AuthenticationRepositoryImpl
import com.asetec.domain.repository.user.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}