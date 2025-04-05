package com.asetec.domain.model.message

import kotlinx.serialization.Serializable

@Serializable
data class DeleteResponse(
    val message: String
)
