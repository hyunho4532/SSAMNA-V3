package com.asetec.presentation.worker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.asetec.presentation.viewmodel.ActivityLocationViewModel
import javax.inject.Inject

class StepCountWorker @Inject constructor (
    context: Context,
    params: WorkerParameters,
    private val activityLocationViewModel: ActivityLocationViewModel
) : Worker(context, params), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    override fun doWork(): Result {
        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }

        return Result.success()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            activityLocationViewModel.activates.value.pedometerCount = event.values[0].toInt()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onStopped() {
        super.onStopped()

        sensorManager.unregisterListener(this)
    }
}