package com.asetec.domain.model.state

import kotlinx.serialization.Serializable

@Serializable
data class  ActivateForm(
    val index: String = "",
    val name: String = "오늘은 출근!",
    val description: String = "",
    val assets: String = "",

    /** 활동 종류 **/
    var activateFormResId: Int = 2131165316,


): ActivityType