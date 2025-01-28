package com.asetec.domain.usecase.json

import com.asetec.domain.model.state.ActivityType
import com.asetec.domain.repository.json.JsonParsingRepository
import javax.inject.Inject

class JsonParseCase @Inject constructor(
    private val jsonParsingRepository: JsonParsingRepository
) {
    fun invoke(jsonFile: String, type: String, onType: (String) -> Unit): List<ActivityType> {
        return jsonParsingRepository.jsonParse(jsonFile, type) {
            onType(it)
        }
    }
}