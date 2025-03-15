package com.asetec.data.modules

import com.asetec.data.repository.crew.CrewRepositoryImpl
import com.asetec.domain.repository.crew.CrewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CrewModule {
    @Binds
    abstract fun bindCrewRepository(
        impl: CrewRepositoryImpl
    ): CrewRepository
}