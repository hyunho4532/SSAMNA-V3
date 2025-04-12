package com.asetec.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.usecase.activate.ActivateCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivateViewModel @Inject constructor(
    private val activateCase: ActivateCase
): ViewModel() {
    suspend fun select(): List<ActivateDTO> {
        return activateCase.selectActivityAll()
    }
}