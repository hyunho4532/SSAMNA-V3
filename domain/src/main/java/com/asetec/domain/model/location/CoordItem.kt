package com.asetec.domain.model.location

import kotlinx.serialization.Serializable

@Serializable
data class CoordItem(
    val coords: List<Coordinate>
)