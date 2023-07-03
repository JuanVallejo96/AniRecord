package com.example.anirecord.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.BottomSheetViewListItemBinding
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.google.android.material.checkbox.MaterialCheckBox

class ShowDetailBottomSheetListsAdapter(
    private val clickHandler: ShowDetailBottomSheetListsClickHandler,
) : RecyclerView.Adapter<ShowDetailBottomSheetListsAdapter.ShowDetailBottomSheetListsViewHolder>() {
    private val items = mutableListOf<Pair<ListCollectionItemModel, Boolean>>()

    interface ShowDetailBottomSheetListsClickHandler {
        fun onListSelectorClick(list: ListCollectionItemModel)
    }

    class ShowDetailBottomSheetListsViewHolder(
        private val binding: BottomSheetViewListItemBinding,
        private val clickHandler: ShowDetailBottomSheetListsClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListCollectionItemModel, checked: Boolean) {
            binding.root.setOnClickListener {
                clickHandler.onListSelectorClick(item)
            }
            binding.textView.text = item.name
            binding.checkBox.checkedState = if (checked) {
                MaterialCheckBox.STATE_CHECKED
            } else {
                MaterialCheckBox.STATE_UNCHECKED
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowDetailBottomSheetListsViewHolder {
        val binding = BottomSheetViewListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowDetailBottomSheetListsViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: ShowDetailBottomSheetListsViewHolder, position: Int) {
        val (item, checked) = items[position]
        holder.bind(item, checked)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(lists: List<Pair<ListCollectionItemModel, Boolean>>) {
        items.clear()
        items.addAll(lists)
        notifyDataSetChanged()
    }
}