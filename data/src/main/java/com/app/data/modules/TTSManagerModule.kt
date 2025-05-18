package com.app.data.modules

import com.app.data.manager.TTSManager
import com.app.data.manager.TTSManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TTSManagerModule {
    @Binds
    abstract fun bindTTSManager(
        impl: TTSManagerImpl
    ) : TTSManager
}