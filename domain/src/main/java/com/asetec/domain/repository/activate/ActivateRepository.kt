package com.asetec.domain.repository.activate

import com.asetec.domain.model.dto.ActivateDTO

interface ActivateRepository {
    suspend fun insert(activateDTO: ActivateDTO, time: (Long) -> Unit)
    suspend fun delete(googleId: String, date: String, onSuccess: (Boolean) -> Unit)
    suspend fun selectActivateByGoogleId(googleId: String) : List<ActivateDTO>
    suspend fun selectActivateByDate(googleId: String, date: String) : List<ActivateDTO>
    suspend fun selectActivityFindById(id: Int) : List<ActivateDTO>
}