package com.asetec.domain.usecase.common

import com.asetec.domain.model.common.Code
import com.asetec.domain.repository.common.CodeRepository
import javax.inject.Inject

class CodeCase @Inject constructor(
    private val codeRepository: CodeRepository
) {
    suspend fun select(): List<Code> {
        return codeRepository.select()
    }
}