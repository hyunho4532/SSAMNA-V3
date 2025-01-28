package com.asetec.presentation.viewmodel

import android.content.Context
import android.hardware.SensorEventListener
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.state.Activate
import com.asetec.domain.usecase.sensor.SensorManagerCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorManagerViewModel @Inject constructor(
    private val sensorManagerCase: SensorManagerCase,
    @ApplicationContext private val appContext: Context
): ViewModel() {

    private var sharedPreferences = appContext.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)

    private val _activates = MutableStateFlow(Activate(
        time = getSavedTimeState()
    ))

    val activates: StateFlow<Activate> = _activates

    private var stopwatchJob: Job? = null

    fun startService(context: Context, isRunning: Boolean) {
        sensorManagerCase.startService(context)

        _activates.update {
            it.copy(
                activateButtonName = "측정 중!",
                isRunning = isRunning
            )
        }

        sharedPreferences.edit().putBoolean("showRunning", isRunning).apply()
        sharedPreferences.edit().putString("activateButtonName", _activates.value.activateButtonName).apply()
    }

    fun stopService(context: Context, runningStatus: Boolean, isRunning: Boolean) {
        sharedPreferences.edit().putInt("pedometerCount", 0).apply()

        sensorManagerCase.stopService(context = context)

        _activates.update {
            it.copy(
                showRunningStatus = runningStatus,
                isRunning = isRunning,
                activateButtonName = "측정하기!"
            )
        }

        sharedPreferences.edit().putBoolean("showRunning", isRunning).apply()
        sharedPreferences.edit().putString("activateButtonName", _activates.value.activateButtonName).apply()
    }

    fun sensorEventListener(): SensorEventListener {
        return sensorManagerCase.sensorListener(getSavedSensorState()) { stepCount ->
            _activates.update {
                it.copy(
                    pedometerCount = stepCount
                )
            }
        }
    }

    fun getSavedSensorState(): Int {
        return sharedPreferences.getInt("pedometerCount", _activates.value.pedometerCount)
    }

    fun getSavedButtonNameState(): String? {
        return sharedPreferences.getString("activateButtonName", _activates.value.activateButtonName)
    }

    fun getSavedTimeState(): Long {
        return sharedPreferences.getLong("time", 0L)
    }

    fun getSavedIsRunningState(): Boolean {
        return sharedPreferences.getBoolean("showRunning", _activates.value.isRunning)
    }

    fun setSavedSensorState() {
        sharedPreferences.edit().putInt("pedometerCount", _activates.value.pedometerCount).apply()
        sharedPreferences.edit().putLong("time", _activates.value.time).apply()
    }

    fun startWatch() {
        if (stopwatchJob == null) {
            stopwatchJob = viewModelScope.launch {
                while (true) {
                    delay(1000L)
                    _activates.update {
                        it.copy(
                            time = it.time + 1
                        )
                    }
                }
            }

            sharedPreferences.edit().putLong("time", _activates.value.time).apply()
        }
    }

    fun stopWatch() {
        stopwatchJob?.cancel()
        stopwatchJob = null
    }

    override fun onCleared() {
        super.onCleared()
        setSavedSensorState()
    }
}