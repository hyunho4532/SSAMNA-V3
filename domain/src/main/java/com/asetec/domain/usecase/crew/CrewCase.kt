package com.asetec.domain.usecase.crew

import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.repository.crew.CrewRepository
import javax.inject.Inject

class CrewCase @Inject constructor(
    private val crewRepository: CrewRepository
) {
    suspend fun insert(crewDTO: CrewDTO) {
        crewRepository.insert(crewDTO)
    }
}