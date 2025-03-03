package com.asetec.domain.usecase.challenge

import com.asetec.domain.model.dto.ChallengeDTO
import com.asetec.domain.repository.challenge.ChallengeRepository
import javax.inject.Inject

class ChallengeCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend fun saveChallenge(challengeDTO: ChallengeDTO) {
        challengeRepository.insert(challengeDTO)
    }

    suspend fun selectChallengeFindById(id: Int) : List<ChallengeDTO> {
        return challengeRepository.selectChallengeFindById(id)
    }

    suspend fun selectChallengeFindByGoogleId(googleId: String) : List<ChallengeDTO> {
        return challengeRepository.selectChallengeFindByGoogleId(googleId)
    }
}