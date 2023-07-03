package com.example.anirecord.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.ListCollectionItemBinding
import com.example.anirecord.domain.model.ListCollectionItemModel


class ListCollectionAdapter(
    private val clickHandler: ListCollectionClickHandler,
) : RecyclerView.Adapter<ListCollectionAdapter.ListCollectionViewHolder>() {
    private val items = mutableListOf<ListCollectionItemModel>()

    interface ListCollectionClickHandler {
        fun onClick(list: ListCollectionItemModel)
        fun onLongClick(view: View, list: ListCollectionItemModel)
    }

    class ListCollectionViewHolder(
        private val binding: ListCollectionItemBinding,
        private val clickHandler: ListCollectionClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListCollectionItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onClick(item)
            }
            binding.root.setOnLongClickListener {
                clickHandler.onLongClick(it, item)
                true
            }
            binding.listOptions.setOnClickListener {
                clickHandler.onLongClick(it, item)
            }
            binding.listName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCollectionViewHolder {
        val binding = ListCollectionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListCollectionViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: ListCollectionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(lists: List<ListCollectionItemModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(lists)
        notifyItemRangeInserted(initPosition, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(lists: List<ListCollectionItemModel>) {
        items.clear()
        items.addAll(lists)
        notifyDataSetChanged()
    }
}