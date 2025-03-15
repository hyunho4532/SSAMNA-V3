package com.asetec.domain.repository.crew

import com.asetec.domain.model.dto.ActivateDTO
import com.asetec.domain.model.dto.CrewDTO

interface CrewRepository {
    suspend fun insert(crewDTO: CrewDTO)
    suspend fun isCrewDataExists(googleId: String): List<CrewDTO>
}