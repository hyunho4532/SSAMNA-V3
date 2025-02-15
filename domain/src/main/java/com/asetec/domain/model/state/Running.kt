package com.asetec.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Running(
    val index: String,
    val status: String,
    val assets: String = ""
): ActivityType