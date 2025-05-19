package com.app.data.manager.tts

import android.content.Context
import android.util.Log
import com.app.domain.model.enum.VoiceType
import com.google.cloud.speech.v1.*
import javax.inject.Inject

class TTSManagerImpl @Inject constructor(
    val context: Context
) : TTSManager {

    /**
     * speechClient 생성
     */
    private val speechClient: SpeechClient = SpeechClient.create()

    private val gcsUri = "gs://cloud-samples-data/speech/brooklyn_bridge.raw"

    private val config: RecognitionConfig = RecognitionConfig.newBuilder()
        .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
        .setSampleRateHertz(16000)
        .setLanguageCode("ko-KR")
        .build()

    override fun speak(text: String, type: VoiceType) {
        val audio: RecognitionAudio = RecognitionAudio.newBuilder().setUri(gcsUri).build()

        val response: RecognizeResponse = speechClient.recognize(config, audio)
        val results: List<SpeechRecognitionResult> = response.resultsList

        for (result: SpeechRecognitionResult in results) {
            val alternative: SpeechRecognitionAlternative = result.alternativesList[0]
            Log.d("TTSManagerImpl", alternative.toString())
        }
    }
}