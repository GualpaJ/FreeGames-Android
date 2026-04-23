package com.javier.freegames.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.javier.freegames.data.Game
import com.javier.freegames.databinding.ActivityDetailBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.Log.e
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.javier.freegames.R
import com.javier.freegames.data.GameService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.javier.freegames.data.TranslateApi
import com.javier.freegames.data.TranslateRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    lateinit var game: Game

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

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        }
    }

    fun loadData() {
        // Log.i("TRANSLATE_DEBUG", "ENTRO EN loadData()") esto era para debug de la api
        supportActionBar?.title = game.title

        binding.titleTextView.text = game.title
        Picasso.get().load(game.image).into(binding.thumbnailImageView)
        binding.gameUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(game.gameURL.toUri())
            startActivity(intent)
        }
        Log.i("GAME_DEBUG", "ID del juego: ${game.id}")
        //binding.descriptionTextView.text = game.description -- Descripcion nativa de la API
        // Traducimos el texto mediante la API de mymemory.translated.
        // Link de la documentacion : https://mymemory.translated.net/doc/spec.php
        CoroutineScope(Dispatchers.IO).launch {
            val fullText = game.description ?: ""

            try {
                // 1. Dividimos por párrafos (saltos de línea)
                val paragraphs = fullText.split("\n\n", "\n") // Separa por doble o simple salto de línea
                val translatedParts = mutableListOf<String>()

                Log.i("TRANSLATE_DEBUG", "Párrafos encontrados: ${paragraphs.size}")

                // 2. Traducimos cada párrafo
                for ((index, paragraph) in paragraphs.withIndex()) {
                    if (paragraph.isBlank()) continue

                    // Si un párrafo es muy largo (>400 chars), lo dividimos
                    val chunks = if (paragraph.length > 400) {
                        paragraph.chunked(400)
                    } else {
                        listOf(paragraph)
                    }

                    for (chunk in chunks) {
                        val response = TranslateApi.service.translate(chunk, "en|es")
                        val translated = response.responseData.translatedText
                        translatedParts.add(translated)
                    }

                    // Añadir salto de línea entre párrafos traducidos
                    if (index < paragraphs.size - 1) {
                        translatedParts.add("\n\n")
                    }
                }

                // 3. Unimos todo
                val finalText = translatedParts.joinToString(" ")

                // 4. Actualizamos UI
                withContext(Dispatchers.Main) {
                    binding.descriptionTextView.text = finalText
                }
            } catch (e: Exception) {
                Log.e("TRANSLATE_DEBUG", "Error: ${e.message}")
                withContext(Dispatchers.Main) {
                    binding.descriptionTextView.text = fullText
                }
            }
        }

    }
}