package com.asetec.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivateDTO(
    @SerialName("google_id")
    val googleId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("status_icon")
    val statusIcon: Int = 0,

    @SerialName("status_title")
    val statusTitle: String = "",

    @SerialName("goal_count")
    val goalCount: Int = 0,

    @SerialName("time")
    val time: String = "",

    @SerialName("kcal_cul")
    val kcal_cul: Double = 0.0,

    @SerialName("km_cul")
    val km_cul: Double = 0.0,

    @SerialName("today_format")
    val todayFormat: String = ""
)