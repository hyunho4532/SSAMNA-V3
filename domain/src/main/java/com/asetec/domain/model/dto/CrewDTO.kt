package com.asetec.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewDTO(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("user_id")
    val userId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("picture")
    val picture: String = "",

    @SerialName("created_at")
    val createdAt: String = "",

    var crewExists: String = ""
)