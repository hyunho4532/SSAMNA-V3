package com.asetec.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Challenge(
    val index: Int = 0,
    val description: String = "",
    val name: String = "",
    val goal: Int = 0,
    val type: String = ""
): ActivityType