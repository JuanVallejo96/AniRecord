package com.example.anirecord.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.R
import com.example.anirecord.databinding.SeriesListItemBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.squareup.picasso.Picasso

class SeriesListAdapter(private val clickHandler: SeriesClickHandler) :
    RecyclerView.Adapter<SeriesListAdapter.SeriesViewHolder>() {
    private val items = mutableListOf<ShowListItemModel>()

    interface SeriesClickHandler {
        fun onShowClick(show: ShowListItemModel)
    }

    class SeriesViewHolder(
        private val binding: SeriesListItemBinding,
        private val clickHandler: SeriesClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onShowClick(item)
            }
            binding.showTitle.text = item.name ?: itemView.context.getText(R.string.unknown_label)
            Picasso.get().load(item.cover).into(binding.showCover)
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