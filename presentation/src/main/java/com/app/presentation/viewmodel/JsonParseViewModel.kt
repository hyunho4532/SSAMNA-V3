package com.app.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.app.domain.model.state.Activate
import com.app.domain.model.state.ActivateForm
import com.app.domain.model.state.Challenge
import com.app.domain.model.state.Crew
import com.app.domain.usecase.json.JsonParseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JsonParseViewModel @Inject constructor(
    private val jsonParseCase: JsonParseCase,
) : ViewModel() {

    private val _activateJsonData = mutableListOf<Activate>()
    val activateJsonData: List<Activate> = _activateJsonData

    private val _activateFormJsonData = mutableListOf<ActivateForm>()
    val activateFormJsonData: List<ActivateForm> = _activateFormJsonData

    private val _challengeJsonData = mutableStateListOf<Challenge>()
    val challengeJsonData: List<Challenge> = _challengeJsonData

    private val _crewJsonData = mutableStateListOf<Crew>()
    val crewJsonData: List<Crew> = _crewJsonData

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
                "activate_form" -> {
                    for (activateForm in activityData) {
                        _activateFormJsonData.add(activateForm as ActivateForm)
                    }
                }
                "challenge" -> {
                    for (challenge in activityData) {
                        _challengeJsonData.add(challenge as Challenge)
                    }
                }
                "crew" -> {
                    for (crew in activityData) {
                        _crewJsonData.add(crew as Crew)
                    }
                }
            }
        }
    }

    fun dataFromJson(data: String, type: String): List<Any> {
        return jsonParseCase.dataFromJson(data, type)
    }
}