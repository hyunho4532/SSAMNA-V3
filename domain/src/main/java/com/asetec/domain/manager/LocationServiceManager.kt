package com.asetec.domain.manager

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface LocationServiceManager {
    fun startLocationService(context: Context)
    fun stopLocationService(context: Context)
    fun getLatitude(): Double
    fun getLongitude(): Double
    suspend fun locationFlow(): Flow<Pair<Double, Double>>
}