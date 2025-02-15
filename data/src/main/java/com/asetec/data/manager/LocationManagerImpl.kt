package com.asetec.data.manager

import android.content.Context
import android.content.Intent
import android.os.Build
import com.asetec.data.service.LocationService
import com.asetec.domain.manager.LocationServiceManager

class LocationManagerImpl : LocationServiceManager {
    override fun startLocationService(context: Context) {
        val intent = Intent(context, LocationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    override fun stopLocationService(context: Context) {
        val intent = Intent(context, LocationService::class.java)
        context.stopService(intent)
    }
}