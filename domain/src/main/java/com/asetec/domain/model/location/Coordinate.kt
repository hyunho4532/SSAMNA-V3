package com.asetec.domain.model.location

import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val coordzState: Boolean = false,
    val coordz: List<Location> = emptyList()
)