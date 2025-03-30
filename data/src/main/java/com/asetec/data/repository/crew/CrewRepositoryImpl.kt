package com.asetec.data.repository.crew

import android.util.Log
import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.domain.model.dto.ChallengeDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.repository.crew.CrewRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
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

    override suspend fun crewFindById(googleId: String): List<CrewDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Crew").select {
                filter {
                    eq("user_id ", googleId)
                }
            }.decodeList<CrewDTO>()
        }
    }

    override suspend fun notificationAll(): List<ActivateNotificationDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("ActivateNotification").select {

            }.decodeList<ActivateNotificationDTO>()
        }
    }

    override suspend fun crewCount(crewId: Int): Int {
        return withContext(Dispatchers.IO) {
            val response = postgrest
                .rpc("get_crew_count", mapOf("crewid" to crewId))

            // response.data가 Int가 아닐 경우 처리
            response.data.toInt()
        }
    }

    override suspend fun crewSumFeed(crewId: Int): Int {
        return withContext(Dispatchers.IO) {
            val response = postgrest
                .rpc("get_sum_feed", mapOf("crewid" to crewId))

            response.data.toInt()
        }
    }
}