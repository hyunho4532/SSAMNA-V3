package com.asetec.domain.repository.common

import com.asetec.domain.model.common.Code

interface CodeRepository {
    suspend fun select(): List<Code>
}