package com.example.anirecord.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.BottomSheetViewListItemBinding
import com.example.anirecord.domain.model.ListCollectionItemModel

class ShowDetailBottomSheetListsAdapter(
    private val clickHandler: ShowDetailBottomSheetListsClickHandler,
) : RecyclerView.Adapter<ShowDetailBottomSheetListsAdapter.ShowDetailBottomSheetListsViewHolder>() {
    private val items = mutableListOf<ListCollectionItemModel>()

    interface ShowDetailBottomSheetListsClickHandler {
        fun onClick(list: ListCollectionItemModel)
    }

    class ShowDetailBottomSheetListsViewHolder(
        private val binding: BottomSheetViewListItemBinding,
        private val clickHandler: ShowDetailBottomSheetListsClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListCollectionItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onClick(item)
            }
            binding.textView.text = item.name
            binding.imageView.isVisible = false
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(lists: List<ListCollectionItemModel>) {
        items.clear()
        items.addAll(lists)
        notifyDataSetChanged()
    }
}