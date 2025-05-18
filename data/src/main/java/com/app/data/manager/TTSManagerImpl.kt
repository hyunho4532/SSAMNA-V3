package com.app.data.manager

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import javax.inject.Inject

class TTSManagerImpl @Inject constructor(
    private val context: Context
) : TTSManager, TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.KOREAN)
        }
    }

    override fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}