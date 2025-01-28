package com.asetec.domain.usecase.sensor

import android.content.Context
import android.hardware.SensorEventListener
import com.asetec.domain.manager.SensorServiceManager
import javax.inject.Inject

class SensorManagerCase @Inject constructor(
    private val sensorServiceManager: SensorServiceManager
) {
    fun startService(context: Context) {
        sensorServiceManager.startSensorService(context)
    }

    fun stopService(context: Context) {
        sensorServiceManager.stopSensorService(context)
    }

    fun sensorListener(stepCount: Int, setStepCount: (Int) -> Unit): SensorEventListener {
        return sensorServiceManager.sensorListener(stepCount) {
            setStepCount(it)
        }
    }

    fun activitySave() {

    }
}