package com.example.anirecord.ui.staffshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.R
import com.example.anirecord.databinding.StaffShowsListItemBinding
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.squareup.picasso.Picasso

class StaffShowsAdapter(
    private val clickHandler: StaffShowClickHandler
) : RecyclerView.Adapter<StaffShowsAdapter.StaffShowsViewHolder>() {
    private val items = mutableListOf<StaffShowListItemModel>()

    interface StaffShowClickHandler {
        fun onShowClick(show: StaffShowListItemModel)
    }

    class StaffShowsViewHolder(
        private val binding: StaffShowsListItemBinding,
        private val clickHandler: StaffShowClickHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StaffShowListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onShowClick(item)
            }
            binding.showTitle.text = item.name ?: itemView.context.getText(R.string.unknown_label)
            binding.roleInfo.text = item.staffRole ?: ""
            Picasso.get().load(item.cover).into(binding.showCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffShowsViewHolder {
        val binding = StaffShowsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return StaffShowsViewHolder(binding, clickHandler)
    }

    fun update(shows: List<StaffShowListItemModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(shows)
        notifyItemRangeInserted(initPosition, items.size)
    }


    override fun onBindViewHolder(holder: StaffShowsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}