package com.example.anirecord.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.SeriesListItemBinding
import com.example.anirecord.domain.model.SeriesModel
import com.squareup.picasso.Picasso

class SeriesListAdapter : RecyclerView.Adapter<SeriesListAdapter.SeriesViewHolder>() {
    private val items = mutableListOf<SeriesModel>()

    class SeriesViewHolder(private val binding: SeriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SeriesModel) {
            binding.seriesTitle.text = item.name ?: "Unknown"
            Picasso.get().load(item.cover).into(binding.seriesCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding =
            SeriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(newItems: List<SeriesModel>) {
        val initPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(initPosition, items.size)
    }

    override fun getItemCount(): Int = items.size
}