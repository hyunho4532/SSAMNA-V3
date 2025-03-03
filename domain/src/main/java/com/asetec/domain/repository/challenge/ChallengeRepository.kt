package com.asetec.domain.repository.challenge

import com.asetec.domain.model.dto.ChallengeDTO


interface ChallengeRepository {
    suspend fun insert(challengeDTO: ChallengeDTO)

    suspend fun selectChallengeFindById(id: Int) : List<ChallengeDTO>

    suspend fun selectChallengeFindByGoogleId(googleId: String) : List<ChallengeDTO>

    suspend fun delete(id: Int, onSuccess: (Boolean) -> Unit)
}