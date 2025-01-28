package com.asetec.data.repository.json

import android.content.Context
import android.content.res.AssetManager
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.state.ActivityType
import com.asetec.domain.model.state.Challenge
import com.asetec.domain.model.state.Running
import com.asetec.domain.repository.json.JsonParsingRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.utils.io.core.use
import javax.inject.Inject

class JsonParsingRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
): JsonParsingRepository {

    private val gson = Gson()

    override fun jsonParse(jsonFile: String, type: String, onType: (String) -> Unit): List<ActivityType> {

        val listType = when (type) {
            "activate" -> {
                object : TypeToken<List<Activate>>() {}.type
            }
            "running" -> {
                object : TypeToken<List<Running>>() {}.type
            }
            else -> {
                object : TypeToken<List<Challenge>>() {}.type
            }
        }

        val assetManager: AssetManager = context.assets

        val json: String = assetManager.open(jsonFile).bufferedReader().use {
            it.readText()
        }

        onType(type)

        return gson.fromJson(json, listType)
    }
}