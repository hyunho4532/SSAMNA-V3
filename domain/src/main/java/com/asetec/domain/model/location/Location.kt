package com.asetec.domain.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    /** 위도, 경도 **/
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
)