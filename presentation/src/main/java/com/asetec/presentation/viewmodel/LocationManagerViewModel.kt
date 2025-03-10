package com.asetec.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.location.Coordinate
import com.asetec.domain.model.location.Location
import com.asetec.domain.usecase.location.LocationManagerCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationManagerViewModel @Inject constructor(
    private val locationManagerCase: LocationManagerCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _coordinate = MutableStateFlow(Coordinate())
    val coordinate: StateFlow<Coordinate> = _coordinate

    init {
        viewModelScope.launch {
            _coordinate.collectLatest { coord ->
                if (coord.coordzState) {
                    /**
                     * 기존 값이 남아 있을 수 있으므로 클리어한다.
                     */
                    _coordinate.value.coordz.subList(0, _coordinate.value.coordz.size)

                    locationManagerCase.getCurrentLocation().collectLatest { (locations: List<Double>, km) ->
                        _coordinate.value.coordz.add(Location(locations[0], locations[1], locations[2], km))
                    }

                }
            }

        }
    }

    fun startService() {
        locationManagerCase.startService(appContext)

        _coordinate.update {
            it.copy(
                coordzState = true
            )
        }
    }

    fun stopService() {
        locationManagerCase.stopService(appContext)

        _coordinate.update {
            it.copy(
                coordzState = false
            )
        }
    }
}