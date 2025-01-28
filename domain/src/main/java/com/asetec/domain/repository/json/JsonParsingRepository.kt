package com.asetec.domain.repository.json

import com.asetec.domain.model.state.ActivityType

interface JsonParsingRepository {
    fun jsonParse(jsonFile: String, type: String, onType: (String) -> Unit): List<ActivityType>
}