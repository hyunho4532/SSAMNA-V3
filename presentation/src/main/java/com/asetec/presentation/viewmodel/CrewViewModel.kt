package com.asetec.presentation.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asetec.domain.model.calcul.FormatImpl
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.model.state.Crew
import com.asetec.domain.usecase.crew.CrewCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrewViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    val crewCase: CrewCase
): ViewModel() {
    private val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _crew = MutableStateFlow(CrewDTO())
    private val crew: StateFlow<CrewDTO> = _crew

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCrew(data: Crew) {

        val userId = sharedPreferences.getString("id", "").toString()

        val crewDTO = CrewDTO(
            userId = userId,
            title = data.name,
            createdAt = FormatImpl("YY:MM:DD:H").getTodayFormatDate(),
        )

        viewModelScope.launch {
            crewCase.insert(crewDTO)
        }
    }
}