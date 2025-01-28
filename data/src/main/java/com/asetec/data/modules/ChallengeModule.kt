package com.asetec.data.modules

import com.asetec.data.repository.challenge.ChallengeRepositoryImpl
import com.asetec.domain.repository.challenge.ChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ChallengeModule {
    @Binds
    abstract fun bindChallengeRepository(
        impl: ChallengeRepositoryImpl
    ): ChallengeRepository
}