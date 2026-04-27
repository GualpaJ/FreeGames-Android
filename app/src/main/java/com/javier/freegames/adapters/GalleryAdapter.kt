package com.javier.freegames.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.freegames.data.Screenshot
import com.javier.freegames.databinding.ItemGalleryImageBinding
import com.squareup.picasso.Picasso

class GalleryAdapter(private val screenshots: List<Screenshot>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemGalleryImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(screenshots[position])
    }

    override fun getItemCount(): Int = screenshots.size

    class GalleryViewHolder(private val binding: ItemGalleryImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(screenshot: Screenshot) {
            Picasso.get()
                .load(screenshot.image)
                .into(binding.galleryImageView)
        }
    }
}