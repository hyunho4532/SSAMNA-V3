package com.asetec.data.modules

import com.asetec.data.repository.sensor.SensorRepositoryImpl
import com.asetec.domain.repository.sensor.SensorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SensorModule {
    @Binds
    abstract fun bindSensorRepository(
        impl: SensorRepositoryImpl
    ): SensorRepository
}