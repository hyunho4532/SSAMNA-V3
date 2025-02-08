package com.asetec.domain.model.dto

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

    @SerialName("running_icon")
    val runningIcon: Int = 0,

    @SerialName("running_title")
    val runningTitle: String = "",

    @SerialName("running_form_icon")
    val runningFormIcon: Int = 0,

    @SerialName("running_form_title")
    val runningFormTitle: String = "",

    @SerialName("goal_count")
    val goalCount: Int = 0,

    @SerialName("time")
    val time: String = "",

    @SerialName("kcal_cul")
    val kcal_cul: Double = 0.0,

    @SerialName("km_cul")
    val km_cul: Double = 0.0,

    @SerialName("today_format")
    val todayFormat: String = "",

    @SerialName("eq_date")
    val eqDate: String = ""
)