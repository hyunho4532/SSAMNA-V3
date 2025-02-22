package com.asetec.domain.repository.challenge

import com.asetec.domain.model.dto.ChallengeDTO


interface ChallengeRepository {
    suspend fun insert(challengeDTO: ChallengeDTO)

    suspend fun selectChallengeFindById(googleId: String) : List<ChallengeDTO>
}