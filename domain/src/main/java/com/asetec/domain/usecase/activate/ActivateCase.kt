package com.asetec.domain.usecase.activate

import com.asetec.domain.model.state.ActivateDTO
import com.asetec.domain.repository.activate.ActivateRepository
import javax.inject.Inject

class ActivateCase @Inject constructor(
    private val activateRepository: ActivateRepository
) {
    suspend fun saveActivity(activateDTO: ActivateDTO, onTime: (Long) -> Unit) {
        activateRepository.insert(activateDTO) {
            onTime(it)
        }
    }

    suspend fun selectActivityFindById(googleId: String) : List<ActivateDTO> {
        return activateRepository.selectActivateById(googleId)
    }
}