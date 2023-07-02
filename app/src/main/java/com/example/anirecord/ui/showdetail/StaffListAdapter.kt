package com.example.anirecord.ui.showdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.ShowDetailCharacterListItemBinding
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.squareup.picasso.Picasso

class StaffListAdapter(
    private val clickHandler: StaffClickHandler,
    private val items: List<ShowStaffListItemModel>
) :
    RecyclerView.Adapter<StaffListAdapter.StaffListViewHolder>() {

    interface StaffClickHandler {
        fun onStaffClickHandler(staff: ShowStaffListItemModel)
    }

    class StaffListViewHolder(
        private val binding: ShowDetailCharacterListItemBinding,
        private val clickHandler: StaffClickHandler,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowStaffListItemModel) {
            binding.characterListItemSubInfo.visibility = View.VISIBLE
            binding.characterListItemMainInfo.text = item.name
            binding.characterListItemSubInfo.text = item.role
            Picasso.get().load(item.image).into(binding.characterListItemImage)
            binding.root.setOnClickListener {
                clickHandler.onStaffClickHandler(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffListViewHolder {
        val binding =
            ShowDetailCharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return StaffListViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: StaffListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}