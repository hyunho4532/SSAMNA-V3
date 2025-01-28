package com.asetec.data.modules

import com.asetec.data.repository.json.JsonParsingRepositoryImpl
import com.asetec.domain.repository.json.JsonParsingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class JsonParsingModule {
    @Binds
    abstract fun bindJsonParsingRepository(
        impl: JsonParsingRepositoryImpl
    ): JsonParsingRepository
}