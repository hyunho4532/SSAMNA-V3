package com.asetec.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivateNotificationDTO(
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("created_at")
    val createdAt: String = ""
)