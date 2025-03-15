package com.asetec.data.repository.crew

import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.repository.crew.CrewRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class CrewRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CrewRepository {
    override suspend fun insert(crewDTO: CrewDTO) {
        postgrest.from("Crew").insert(crewDTO)
    }
}