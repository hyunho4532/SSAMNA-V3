package com.asetec.domain.usecase.crew

import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.repository.crew.CrewRepository
import javax.inject.Inject

class CrewCase @Inject constructor(
    private val crewRepository: CrewRepository
) {
    suspend fun insert(crewDTO: CrewDTO) {
        crewRepository.insert(crewDTO)
    }

    suspend fun isCrewDataExists(googleId: String): List<CrewDTO> {
        return crewRepository.isCrewDataExists(googleId)
    }

    suspend fun crewFindById(googleId: String): List<CrewDTO> {
        return crewRepository.crewFindById(googleId)
    }

    suspend fun notificationAll(): List<ActivateNotificationDTO> {
        return crewRepository.notificationAll()
    }

    suspend fun crewCount(crewId: Int): Int {
        return crewRepository.crewCount(crewId)
    }

    suspend fun crewSumFeed(crewId: Int): Int {
        return crewRepository.crewSumFeed(crewId)
    }
}