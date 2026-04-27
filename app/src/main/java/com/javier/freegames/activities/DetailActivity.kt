package com.javier.freegames.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.javier.freegames.data.Game
import com.javier.freegames.databinding.ActivityDetailBinding
import android.content.Intent
import android.net.Uri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.javier.freegames.R
import com.javier.freegames.data.GameService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.javier.freegames.adapters.GalleryAdapter


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
        supportActionBar?.title = game.title

        binding.titleTextView.text = game.title.uppercase()
        Picasso.get()
            .load(game.screenshots?.firstOrNull()?.image)
            .into(binding.thumbnailImageView)
        binding.gameUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(game.gameURL.toUri())
            startActivity(intent)
        }
        binding.descriptionTextView.text = game.shortDescription

        setupGallery()
    }

    private fun setupGallery() {
        val screenshots = game.screenshots ?: emptyList()

        if (screenshots.isNotEmpty()) {
            val layoutManager = GridLayoutManager(this, 2)

            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val totalItems = screenshots.size
                    // Si es la última posición Y el total es impar -> ocupa 2 columnas
                    return if (position == totalItems - 1 && totalItems % 2 != 0) 2 else 1
                }
            }

            binding.galleryRecyclerView.layoutManager = layoutManager
            binding.galleryRecyclerView.adapter = GalleryAdapter(screenshots)
        } else {
            binding.galleryTextView.visibility = android.view.View.GONE
            binding.galleryRecyclerView.visibility = android.view.View.GONE
        }
    }
}