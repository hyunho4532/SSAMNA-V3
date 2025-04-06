package com.asetec.domain.model.common

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Code(
    @SerializedName("code")
    val code: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("img_path")
    val imgPath: String = ""
)