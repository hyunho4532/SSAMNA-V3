package com.asetec.domain.usecase.challenge

import com.asetec.domain.model.state.ChallengeDTO
import com.asetec.domain.repository.challenge.ChallengeRepository
import javax.inject.Inject

class ChallengeCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend fun saveChallenge(challengeDTO: ChallengeDTO) {
        challengeRepository.insert(challengeDTO)
    }

    suspend fun selectChallengeFindById(googleId: String) : List<ChallengeDTO> {
        return challengeRepository.selectChallengeFindById(googleId)
    }
}