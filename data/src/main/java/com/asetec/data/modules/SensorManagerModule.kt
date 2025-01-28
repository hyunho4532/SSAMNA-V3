package com.asetec.data.modules

import com.asetec.data.manager.SensorManagerImpl
import com.asetec.domain.manager.SensorServiceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SensorManagerModule {
    @Binds
    abstract fun bindSensorManager(
        impl: SensorManagerImpl
    ): SensorServiceManager
}