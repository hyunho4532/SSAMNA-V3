package com.asetec.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.asetec.data.service.LocationService
import com.asetec.domain.manager.LocationServiceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : LocationServiceManager {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.ssamna.LOCATION_UPDATE") {
                latitude = intent.getDoubleExtra("latitude", 0.0)
                longitude = intent.getDoubleExtra("longitude", 0.0)
            }
        }
    }

    init {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(locationReceiver)
    }

    override fun startLocationService(context: Context) {
        val filter = IntentFilter("com.ssamna.LOCATION_UPDATE")
        val intent = Intent(context, LocationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(locationReceiver, filter, Context.RECEIVER_EXPORTED)
            }
        } else {
            context.startService(intent)
        }
    }

    override fun stopLocationService(context: Context) {
        val intent = Intent(context, LocationService::class.java)
        context.stopService(intent)
        LocalBroadcastManager.getInstance(context).unregisterReceiver(locationReceiver)
    }

    override fun getLatitude(): Double = latitude

    override fun getLongitude(): Double = longitude

    override suspend fun locationFlow(): Flow<Pair<Double, Double>> = flow {
        while (true) {
            emit(Pair(getLatitude(), getLongitude()))
            delay(5000)
        }
    }
}