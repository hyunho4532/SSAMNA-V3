package com.asetec.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.location.Location
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.model.state.ActivateForm
import com.asetec.domain.usecase.activate.ActivateCase
import com.asetec.presentation.component.util.FormatImpl
import com.asetec.presentation.component.util.JsonObjImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityLocationViewModel @Inject constructor(
    private val activateCase: ActivateCase?,
    @ApplicationContext appContext: Context?
): ViewModel() {

    private var sharedPreferences = appContext?.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)
    private val sharedPreferences2 = appContext?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _locations = MutableStateFlow(Location(
        latitude = 0.0,
        longitude = 0.0
    ))

    private val _activates = MutableStateFlow(Activate())

    private val _activatesForm = MutableStateFlow(ActivateForm())

    private val _activateData = MutableStateFlow<List<ActivateDTO>>(emptyList())

    val activateData: StateFlow<List<ActivateDTO>> = _activateData

    val locations: StateFlow<Location> = _locations
    val activates: StateFlow<Activate> = _activates
    val activatesForm: StateFlow<ActivateForm> = _activatesForm

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationClient: FusedLocationProviderClient,
        isLocationLoaded: (Boolean) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                _locations.value = Location(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                isLocationLoaded(true)
            } else {
                isLocationLoaded(false)
            }
        }
    }

    fun setActivateName(activateResId: Int, activateName: String) {
        _activates.update {
            it.copy(
                activateResId = activateResId,
                activateName = activateName
            )
        }
    }

    fun setActivateFormName(activateFormResId: Int, activateFormName: String) {
        _activatesForm.update {
            it.copy(
                activateFormResId = activateFormResId,
                name = activateFormName
            )
        }
    }

    fun closeMarkerPopup() {
        _activatesForm.update {
            it.copy(
                showMarkerPopup = false
            )
        }
    }

    fun setLatLng(
        latitude: Double,
        longitude: Double
    ) {
        _activatesForm.update {
            it.copy(
                latitude = latitude,
                longitude = longitude,
                showMarkerPopup = false
            )
        }
    }

    fun statusClick(name: String, resId: Int) {
        _activates.update {
            it.copy(
                statusName = name,
                statusIcon = resId
            )
        }
    }

    fun updateRunningTitle(newTitle: String) {
        _activates.value = _activates.value.copy(runningTitle = newTitle)
    }


    /**
     * 활동 저장 버튼 클릭 시 활동 테이블에 데이터 저장
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveActivity(
        runningIcon: Int,
        runningTitle: String,
        coordinate: List<LatLng>
    ) {
        val pedometerCount = sharedPreferences?.getInt("pedometerCount", _activates.value.pedometerCount)
        val googleId = sharedPreferences2?.getString("id", "")
        val time = sharedPreferences?.getLong("time", _activates.value.time)

        /**
         * kcal_cul, km_cul를 JSON 형태로 만든다.
         */
        val culData = JsonObjImpl(
            type = "cul",
            pedometerCount = pedometerCount!!
        ).build()

        val statusData = JsonObjImpl(
            type = "status",
            activate = _activates
        ).build()

        val runningData = JsonObjImpl(
            type = "running",
            runningList = arrayOf(runningIcon, runningTitle)
        ).build()

        val runningFormData = JsonObjImpl(
            type = "runningForm",
            activateForm = _activatesForm
        ).build()

        val coordinateData = JsonObjImpl(
            type = "coordinate",
            coordinateList = coordinate
        ).build()

        val activateDTO = ActivateDTO (
            googleId = googleId!!,
            title = _activates.value.runningTitle,
            coord = coordinateData,
            status = statusData,
            running = runningData,
            runningForm = runningFormData,
            time = FormatImpl("YY:MM:DD:H").getFormatTime(time!!),
            cul = culData,
            todayFormat = FormatImpl("YY:MM:DD:H").getTodayFormatDate(),
            eqDate = FormatImpl("YY:MM:DD").getTodayFormatDate()
        )

        viewModelScope.launch {
            activateCase?.saveActivity(activateDTO = activateDTO) {
                sharedPreferences?.edit()!!.putLong("time", it).apply()
            }
        }
    }

    suspend fun selectActivityFindById(googleId: String) {
        val activateDTO = activateCase?.selectActivityFindById(googleId)
        _activateData.value = activateDTO!!
    }

    suspend fun selectActivityFindByIdDate(googleId: String, date: String) {
        val activateDTO = activateCase?.selectActivityFindByIdDate(googleId, date)
        _activateData.value = activateDTO!!
    }

    suspend fun selectActivityFindByDate(date: String) {
        val googleId = sharedPreferences2?.getString("id", "")

        val activateDTO = activateCase?.selectActivityFindByDate(googleId!!, date)
        _activateData.value = activateDTO!!
    }
}