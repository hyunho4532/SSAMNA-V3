package com.asetec.domain.repository.crew

import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.model.state.Ranking

interface CrewRepository {
    suspend fun insert(crewDTO: CrewDTO)
    suspend fun delete(crewId: Int, googleId: String)
    suspend fun isCrewDataExists(googleId: String): List<CrewDTO>
    suspend fun crewFindById(googleId: String): List<CrewDTO>
    suspend fun notificationAll(): List<ActivateNotificationDTO>
    suspend fun crewCount(crewId: Int): Int
    suspend fun crewSumFeed(crewId: Int): Int
    suspend fun crewRankingTop3(crewId: Int): List<Ranking>
}