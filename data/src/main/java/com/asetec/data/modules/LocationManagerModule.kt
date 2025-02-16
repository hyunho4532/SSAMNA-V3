package com.asetec.data.modules

import com.asetec.data.manager.LocationManagerImpl
import com.asetec.domain.manager.LocationServiceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationManagerModule {
    @Binds
    abstract fun bindLocationManager(
        impl: LocationManagerImpl
    ): LocationServiceManager
}