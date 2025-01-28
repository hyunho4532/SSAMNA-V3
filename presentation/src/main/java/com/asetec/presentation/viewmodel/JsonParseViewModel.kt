package com.asetec.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.asetec.domain.model.state.Activate
import com.asetec.domain.model.state.ActivityType
import com.asetec.domain.model.state.Challenge
import com.asetec.domain.model.state.Running
import com.asetec.domain.usecase.json.JsonParseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JsonParseViewModel @Inject constructor(
    private val jsonParseCase: JsonParseCase,
) : ViewModel() {

    private val _activateJsonData = mutableListOf<Activate>()
    val activateJsonData: List<Activate> = _activateJsonData

    private val _runningJsonData = mutableStateListOf<Running>()
    val runningJsonData: List<Running> = _runningJsonData

    private val _challengeJsonData = mutableStateListOf<Challenge>()
    val challengeJsonData: List<Challenge> = _challengeJsonData

    fun activateJsonParse(fileName: String, type: String) {

        var setType = ""

        jsonParseCase.invoke(fileName, type) { result ->
            setType = result
        }.let { activityData ->
            when (setType) {
                "activate" -> {
                    for (activate in activityData) {
                        _activateJsonData.add(activate as Activate)
                    }
                }
                "running" -> {
                    for (running in activityData) {
                        _runningJsonData.add(running as Running)
                    }
                }
                "challenge" -> {
                    for (challenge in activityData) {
                        _challengeJsonData.add(challenge as Challenge)
                    }
                }
            }
        }
    }
}