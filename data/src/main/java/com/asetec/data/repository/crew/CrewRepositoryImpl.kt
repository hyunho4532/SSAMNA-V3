package com.asetec.data.repository.crew

import com.asetec.domain.model.dto.ChallengeDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.repository.crew.CrewRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CrewRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CrewRepository {
    override suspend fun insert(crewDTO: CrewDTO) {
        postgrest.from("Crew").insert(crewDTO)
    }

    override suspend fun isCrewDataExists(googleId: String): List<CrewDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Crew").select {
                filter {
                    eq("user_id ", googleId)
                }
            }.decodeList<CrewDTO>()
        }
    }
}