package com.asetec.domain.manager

import android.content.Context

interface LocationServiceManager {
    fun startLocationService(context: Context)
    fun stopLocationService(context: Context)
}