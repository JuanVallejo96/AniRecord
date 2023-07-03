package com.example.anirecord.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.R
import com.example.anirecord.databinding.ShowProgressListItemBinding
import com.example.anirecord.domain.model.ShowProgressListItemModel
import com.squareup.picasso.Picasso

class SeriesWithProgressListAdapter(private val clickHandler: SeriesClickHandler) :
    RecyclerView.Adapter<SeriesWithProgressListAdapter.SeriesWithProgressHolder>() {
    private val items = mutableListOf<ShowProgressListItemModel>()

    interface SeriesClickHandler {
        fun onShowClick(show: ShowProgressListItemModel)
    }

    class SeriesWithProgressHolder(
        private val binding: ShowProgressListItemBinding,
        private val clickHandler: SeriesClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowProgressListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onShowClick(item)
            }
            binding.showProgressTitle.text =
                item.name ?: itemView.context.getText(R.string.unknown_label)
            Picasso.get().load(item.cover).into(binding.showProgressCover)
            if (item.totalEpisodes == null) {
                binding.showProgressAiringLabel.text = itemView.context.getString(
                    R.string.show_still_airing_label,
                    item.progress
                )
                binding.linearProgressIndicator.visibility = View.GONE
                binding.showProgressLabel.visibility = View.GONE
                binding.showProgressAiringLabel.visibility = View.VISIBLE
            } else {
                val percent = (100.0 * item.progress / item.totalEpisodes).toInt()
                binding.linearProgressIndicator.progress = percent
                binding.showProgressLabel.text = itemView.context.getString(
                    R.string.watched_progress_label,
                    item.progress,
                    item.totalEpisodes
                )
                binding.linearProgressIndicator.visibility = View.VISIBLE
                binding.showProgressLabel.visibility = View.VISIBLE
                binding.showProgressAiringLabel.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesWithProgressHolder {
        val binding = ShowProgressListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeriesWithProgressHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: SeriesWithProgressHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(shows: List<ShowProgressListItemModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(shows)
        notifyItemRangeInserted(initPosition, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(shows: List<ShowProgressListItemModel>) {
        items.clear()
        items.addAll(shows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}