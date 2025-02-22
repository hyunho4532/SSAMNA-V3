package com.asetec.domain.repository.activate

import com.asetec.domain.model.dto.ActivateDTO

interface ActivateRepository {
    suspend fun insert(activateDTO: ActivateDTO, time: (Long) -> Unit)
    suspend fun selectActivateById(googleId: String) : List<ActivateDTO>
    suspend fun selectActivateByDate(googleId: String, dayLocalDate: String) : List<ActivateDTO>
}