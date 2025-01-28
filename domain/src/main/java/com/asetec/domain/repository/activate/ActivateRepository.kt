package com.asetec.domain.repository.activate

import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.state.ActivateDTO

interface ActivateRepository {
    suspend fun insert(activateDTO: ActivateDTO, time: (Long) -> Unit)
    suspend fun selectActivateById(googleId: String) : List<ActivateDTO>
}