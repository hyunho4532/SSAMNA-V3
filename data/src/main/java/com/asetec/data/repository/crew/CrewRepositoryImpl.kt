package com.asetec.data.repository.crew

import android.util.Log
import com.asetec.domain.model.dto.ActivateNotificationDTO
import com.asetec.domain.model.dto.ChallengeDTO
import com.asetec.domain.model.dto.CrewDTO
import com.asetec.domain.model.state.Ranking
import com.asetec.domain.repository.crew.CrewRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException
import javax.inject.Inject

class CrewRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CrewRepository {
    override suspend fun insert(crewDTO: CrewDTO) {
        postgrest.from("Crew").insert(crewDTO)
    }

    override suspend fun delete(crewId: Int, googleId: String): String {
        Log.d("CrewRepositoryImpl", "${crewId}, $googleId")

        return withContext(Dispatchers.IO) {
            val response = postgrest
                .rpc("delete_crew", mapOf("crewid" to crewId, "userid" to googleId))
                .decodeAs<String>()

            response
        }
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

            val sumFeed = try {
                response.data.toInt()
            } catch (e: NumberFormatException) {
                0
            }

            sumFeed
        }
    }

    override suspend fun crewRankingTop3(crewId: Int): List<Ranking> {
        return withContext(Dispatchers.IO) {
            postgrest
                .rpc("get_crew_ranking_top3", mapOf("crewid" to crewId))
                .decodeList()
        }
    }
}