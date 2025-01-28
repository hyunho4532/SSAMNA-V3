package com.asetec.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDTO(

    @SerialName("google_id")
    val googleId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("today_date")
    val todayDate: String = ""
)
