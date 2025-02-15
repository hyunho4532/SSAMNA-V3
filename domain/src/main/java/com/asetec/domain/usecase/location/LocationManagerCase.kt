package com.asetec.domain.usecase.location

import android.content.Context
import com.asetec.domain.manager.LocationServiceManager
import javax.inject.Inject

class LocationManagerCase @Inject constructor(
    private val locationServiceManager: LocationServiceManager
) {
    fun startService(context: Context) {
        locationServiceManager.stopLocationService(context)
    }

    fun stopService(context: Context) {
        locationServiceManager.stopLocationService(context)
    }
}