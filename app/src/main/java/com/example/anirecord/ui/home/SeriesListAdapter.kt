package com.example.anirecord.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.SeriesListItemBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.squareup.picasso.Picasso

class SeriesListAdapter(private val clickHandler: SeriesClickHandler) :
    RecyclerView.Adapter<SeriesListAdapter.SeriesViewHolder>() {
    private val items = mutableListOf<ShowListItemModel>()

    interface SeriesClickHandler {
        fun onClick(show: ShowListItemModel)
    }

    class SeriesViewHolder(
        private val binding: SeriesListItemBinding,
        private val clickHandler: SeriesClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onClick(item)
            }
            binding.seriesTitle.text = item.name ?: "Unknown"
            Picasso.get().load(item.cover).into(binding.seriesCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = SeriesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeriesViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(shows: List<ShowListItemModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(shows)
        notifyItemRangeInserted(initPosition, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(shows: List<ShowListItemModel>) {
        items.clear()
        items.addAll(shows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}