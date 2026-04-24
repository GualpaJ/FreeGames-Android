package com.javier.freegames.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.javier.freegames.data.Game
import com.javier.freegames.databinding.ActivityDetailBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.javier.freegames.R
import com.javier.freegames.data.GameService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var game: Game
    private var translator: com.google.mlkit.nl.translate.Translator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra("GAME_ID", -1)

        CoroutineScope(Dispatchers.IO).launch {
            game = GameService.getInstance().getGameById(id)

            // Inicializar el traductor offline
            inicializarTraductor()

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        }
    }

    private suspend fun inicializarTraductor() {
        return suspendCancellableCoroutine { continuation ->
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.SPANISH)
                .build()

            translator = Translation.getClient(options)

            translator?.downloadModelIfNeeded()
                ?.addOnSuccessListener {
                    Log.i("MLKIT", "✅ Modelo de traducción listo")
                    continuation.resume(Unit)
                }
                ?.addOnFailureListener { e ->
                    Log.e("MLKIT", "❌ Error descargando modelo: ${e.message}")
                    continuation.resume(Unit) // Continuamos aunque falle
                }
        }
    }

    private suspend fun traducirChunk(texto: String): String {
        return suspendCancellableCoroutine { continuation ->
            translator?.translate(texto)
                ?.addOnSuccessListener { traducido ->
                    continuation.resume(traducido)
                }
                ?.addOnFailureListener { e ->
                    Log.e("MLKIT", "❌ Error traduciendo chunk: ${e.message}")
                    continuation.resume(texto) // Fallback: devolvemos el original
                }
                ?: continuation.resume(texto) // Si translator es null, devolvemos original
        }
    }

    fun loadData() {
        supportActionBar?.title = game.title
        binding.titleTextView.text = game.title
        Picasso.get().load(game.image).into(binding.thumbnailImageView)
        binding.gameUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(game.gameURL.toUri())
            startActivity(intent)
        }
        Log.i("GAME_DEBUG", "ID del juego: ${game.id}")

        // Mostrar texto original inmediatamente (por si la traducción tarda)
        binding.descriptionTextView.text = game.description ?: "Sin descripción disponible"

        // Traducción con ML Kit - SIN CHUNKS
        CoroutineScope(Dispatchers.IO).launch {
            val fullText = game.description ?: ""

            if (fullText.isEmpty()) return@launch

            try {
                // Dividimos por párrafos (saltos de línea)
                val paragraphs = fullText.split("\n\n", "\n")
                val translatedParts = mutableListOf<String>()

                Log.i("TRANSLATE_DEBUG", "Párrafos encontrados: ${paragraphs.size}")

                // Traducimos cada párrafo COMPLETO (sin chunks)
                for ((index, paragraph) in paragraphs.withIndex()) {
                    if (paragraph.isBlank()) continue

                    // Traducir el párrafo entero de una vez
                    val translated = traducirChunk(paragraph)  // El nombre "traducirChunk" queda raro pero funciona
                    translatedParts.add(translated)
                    Log.i("TRANSLATE_DEBUG", "Párrafo ${index + 1} traducido: ${translated.take(100)}...")

                    // Añadir salto de línea entre párrafos traducidos
                    if (index < paragraphs.size - 1) {
                        translatedParts.add("\n\n")
                    }
                }

                // Unimos todo
                val finalText = translatedParts.joinToString(" ")

                // Actualizamos UI
                withContext(Dispatchers.Main) {
                    // Limpiar saltos de línea excesivos para evitar espacios extra
                    val cleanedText = finalText
                        .replace(Regex("\\n\\s*\\n"), "\n\n")  // Saltos dobles normales
                        .replace(Regex("\\n"), " ")            // Saltos simples por espacios
                        .trim()

                    binding.descriptionTextView.text = cleanedText
                    binding.translateTextView.visibility = android.view.View.VISIBLE
                }
            } catch (e: Exception) {
                Log.e("TRANSLATE_DEBUG", "Error: ${e.message}")
                // No hacemos nada porque el texto original ya está visible
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        translator?.close()
    }
}