package com.asetec.domain.usecase.crew

import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.model.state.Ranking
import com.asetec.domain.repository.crew.CrewRepository
import javax.inject.Inject

class CrewCase @Inject constructor(
    private val crewRepository: CrewRepository
) {
    suspend fun insert(crewDTO: CrewDTO) {
        crewRepository.insert(crewDTO)
    }

    suspend fun delete(crewId: Int, userId: String, onResult: (Boolean) -> Unit) {
        crewRepository.delete(crewId, userId) {
            onResult(it)
        }
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

    suspend fun crewRankingTop3(crewId: Int): List<Ranking> {
        return crewRepository.crewRankingTop3(crewId)
    }
}