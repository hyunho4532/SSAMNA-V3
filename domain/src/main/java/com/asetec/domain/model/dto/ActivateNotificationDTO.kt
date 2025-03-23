package com.asetec.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class ActivateNotificationDTO(
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("feed")
    val feed: Double = 0.0,

    @SerialName("crew_id")
    val crewId: JsonObject = buildJsonObject {  },

    @SerialName("created_at")
    val createdAt: String = ""
)