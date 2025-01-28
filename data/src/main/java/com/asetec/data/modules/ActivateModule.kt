package com.asetec.data.modules

import com.asetec.data.repository.activate.ActivateRepositoryImpl
import com.asetec.domain.repository.activate.ActivateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivateModule {
    @Binds
    abstract fun bindActivateRepository(
        impl: ActivateRepositoryImpl
    ): ActivateRepository
}