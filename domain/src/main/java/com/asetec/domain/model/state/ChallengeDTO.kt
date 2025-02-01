package com.asetec.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDTO(

    @SerialName("google_id")
    val googleId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("goal")
    val goal: Int = 0,

    @SerialName("type")
    val type: String = "",

    @SerialName("today_date")
    val todayDate: String = ""
)
