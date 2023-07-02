package com.example.anirecord.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.ListDetailSeriesListItemBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.squareup.picasso.Picasso

class ListDetailAdapter(private val clickHandler: ListDetailItemClickHandler) :
    RecyclerView.Adapter<ListDetailAdapter.ListDetailViewHolder>() {
    private val shows = mutableListOf<ShowListItemModel>()

    interface ListDetailItemClickHandler {
        fun onClick(show: ShowListItemModel)
        fun onLongClick(view: View, show: ShowListItemModel)
    }

    class ListDetailViewHolder(
        private val binding: ListDetailSeriesListItemBinding,
        private val clickHandler: ListDetailItemClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: ShowListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onClick(show)
            }
            binding.root.setOnLongClickListener {
                clickHandler.onLongClick(it, show)
                true
            }
            binding.seriesListItemMenuOption.setOnClickListener {
                clickHandler.onLongClick(it, show)
            }
            Picasso.get().load(show.cover).into(binding.seriesCover)
            binding.seriesTitle.text = show.name ?: "Unknown"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailViewHolder {
        val binding = ListDetailSeriesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListDetailViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: ListDetailViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    override fun getItemCount(): Int = shows.size

    fun update(lists: List<ShowListItemModel>) {
        val initPosition = shows.size
        shows.clear()
        shows.addAll(lists)
        notifyItemRangeInserted(initPosition, shows.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(lists: List<ShowListItemModel>) {
        shows.clear()
        shows.addAll(lists)
        notifyDataSetChanged()
    }
}