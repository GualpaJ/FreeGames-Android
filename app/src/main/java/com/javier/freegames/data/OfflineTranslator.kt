package com.javier.freegames.data

import android.content.Context
import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class OfflineTranslator(private val context: Context) {

    private var translator: Translator? = null
    private var isModelReady = false

    // Inicializar el traductor (inglés -> español)
    suspend fun initialize(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            try {
                val options = TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.ENGLISH)
                    .setTargetLanguage(TranslateLanguage.SPANISH)
                    .build()

                translator = Translation.getClient(options)

                // Condiciones de descarga (solo WiFi recomendado)
                val conditions = DownloadConditions.Builder()
                    .requireWifi()
                    .build()

                // Descargar modelo si no existe
                translator?.downloadModelIfNeeded(conditions)
                    ?.addOnSuccessListener {
                        Log.i("OFFLINE_TRANS", "Modelo de traducción descargado/ok")
                        isModelReady = true
                        continuation.resume(true)
                    }
                    ?.addOnFailureListener { exception ->
                        Log.e("OFFLINE_TRANS", "Error descargando modelo: ${exception.message}")
                        isModelReady = false
                        continuation.resume(false)
                    }
            } catch (e: Exception) {
                Log.e("OFFLINE_TRANS", "Error inicializando: ${e.message}")
                continuation.resume(false)
            }
        }
    }

    // Traducir texto
    suspend fun translate(text: String): String? {
        if (!isModelReady || translator == null) {
            Log.w("OFFLINE_TRANS", "Modelo no listo")
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            translator?.translate(text)
                ?.addOnSuccessListener { translatedText ->
                    continuation.resume(translatedText)
                }
                ?.addOnFailureListener { exception ->
                    Log.e("OFFLINE_TRANS", "Error traduciendo: ${exception.message}")
                    continuation.resume(null)
                }
        }
    }

    // Cerrar el traductor (liberar recursos)
    fun close() {
        translator?.close()
        translator = null
    }
}