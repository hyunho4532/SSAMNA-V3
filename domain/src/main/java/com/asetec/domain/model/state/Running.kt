package com.asetec.domain.model.state

data class Running(
    val index: String,
    val status: String,
    val assets: String = ""
): ActivityType