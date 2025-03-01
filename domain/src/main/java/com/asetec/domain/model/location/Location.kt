package com.asetec.domain.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    /** 위도, 경도, 고도 **/
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var altitude: Double = 0.0
)