package com.asetec.domain.model.entry

import com.asetec.domain.model.enum.ProfileStatusType

data class PolygonBoxItem<T> (
    val title: String,
    val data: T
)