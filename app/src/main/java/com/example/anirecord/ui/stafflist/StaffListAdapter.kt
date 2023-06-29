package com.example.anirecord.ui.stafflist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.StaffListItemBinding
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.squareup.picasso.Picasso

class StaffListAdapter(
    private val clickHandler: StaffClickHandler
) : RecyclerView.Adapter<StaffListAdapter.StaffViewHolder>() {
    private val items = mutableListOf<ShowStaffListItemModel>()

    interface StaffClickHandler {
        fun onStaffClickHandler(voiceActor: ShowStaffListItemModel)
    }

    class StaffViewHolder(
        private val binding: StaffListItemBinding,
        private val clickHandler: StaffClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowStaffListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onStaffClickHandler(item)
            }
            binding.staffListItemName.text = item.name
            Picasso.get().load(item.image).into(binding.staffListItemImage)
            binding.staffListItemRole.text = item.role
        }
    }

    fun update(actors: List<ShowStaffListItemModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(actors)
        notifyItemRangeInserted(initPosition, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = StaffListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StaffViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}