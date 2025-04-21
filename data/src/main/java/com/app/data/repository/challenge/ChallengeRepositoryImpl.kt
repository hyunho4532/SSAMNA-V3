package com.app.data.repository.challenge

import com.app.domain.model.dto.ChallengeDTO
import com.app.domain.model.state.ChallengeMaster
import com.app.domain.repository.challenge.ChallengeRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): ChallengeRepository {
    override suspend fun insert(challengeDTO: ChallengeDTO) {
        postgrest.from("Challenge").insert(challengeDTO)
    }

    override suspend fun selectChallengeAll(): List<ChallengeMaster> {
        return withContext(Dispatchers.IO) {
            postgrest.from("ChallengeMaster").select().decodeList<ChallengeMaster>()
        }
    }

    override suspend fun selectChallengeFindById(id: Int): List<ChallengeDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Challenge").select {
                filter {
                    eq("id", id)
                }
            }.decodeList<ChallengeDTO>()
        }
    }

    override suspend fun selectChallengeFindByGoogleId(googleId: String): List<ChallengeDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Challenge").select {
                filter {
                    eq("google_id", googleId)
                }
            }.decodeList<ChallengeDTO>()
        }
    }

    override suspend fun delete(id: Int, onSuccess: (Boolean) -> Unit) {
        return withContext(Dispatchers.IO) {
            postgrest.from("Challenge").delete {
                filter {
                    eq("id", id)
                }
            }

            onSuccess(true)
        }
    }
}