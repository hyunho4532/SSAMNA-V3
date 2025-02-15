package com.asetec.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.usecase.location.LocationManagerCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationManagerViewModel @Inject constructor(
    private val locationManagerCase: LocationManagerCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    init {
        viewModelScope.launch {
            locationManagerCase.getCurrentLocation().collectLatest { (lat, lng) ->
                Log.d("LocationManagerViewModel", "위도: $lat, 경도: $lng")
            }
        }
    }

    fun startService() {
        locationManagerCase.startService(appContext)
    }

    fun stopService() {
        locationManagerCase.stopService(appContext)
    }
}