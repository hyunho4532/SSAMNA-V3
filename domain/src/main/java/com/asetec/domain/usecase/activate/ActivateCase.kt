package com.asetec.domain.usecase.activate

import com.asetec.domain.model.dto.ActivateDTO
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

    suspend fun deleteActivity(googleId: String, date: String, onSuccess: (Boolean) -> Unit) {
        activateRepository.delete(googleId, date) {
            onSuccess(it)
        }
    }

    suspend fun selectActivityFindById(googleId: String) : List<ActivateDTO> {
        return activateRepository.selectActivateById(googleId)
    }

    suspend fun selectActivityFindByDate(googleId: String, date: String) : List<ActivateDTO> {
        return activateRepository.selectActivateByDate(googleId, date)
    }

    suspend fun selectActivityFindByIdDate(googleId: String, date: String) : List<ActivateDTO> {
        return activateRepository.selectActivityFindByIdDate(googleId, date)
    }
}