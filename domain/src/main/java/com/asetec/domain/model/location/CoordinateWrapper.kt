package com.asetec.domain.model.location

import kotlinx.serialization.Serializable

@Serializable
data class CoordinateWrapper(
    val coords: List<Coordinate>
)