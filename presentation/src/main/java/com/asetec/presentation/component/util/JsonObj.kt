package com.asetec.presentation.component.util

import kotlinx.serialization.json.JsonObject

sealed class JsonObj {
    abstract fun build(): JsonObject
}