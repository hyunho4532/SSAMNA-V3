package com.asetec.domain.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val coordzState: Boolean = false,
    val coordz: MutableList<Location> = ArrayList(),

    val googleId: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val altitude: Double = 0.0,

    val step: Int = 0,
    val km: String = ""
)