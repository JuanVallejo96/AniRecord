package com.example.anirecord.ui.showdetail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.CharacterListItemBinding
import com.example.anirecord.domain.model.CharacterConnectionModel
import com.squareup.picasso.Picasso

class CharacterConnectionListAdapter(
    private val clickHandler: CharacterClickHandler,
    private val items: List<CharacterConnectionModel>
) :
    RecyclerView.Adapter<CharacterConnectionListAdapter.CharacterViewHolder>() {

    interface CharacterClickHandler {
        fun onCharacterClick(show: CharacterConnectionModel)
    }

    class CharacterViewHolder(
        private val binding: CharacterListItemBinding,
        private val clickHandler: CharacterClickHandler
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterConnectionModel) {
            binding.root.setOnClickListener {
                clickHandler.onCharacterClick(item)
            }
            binding.characterListItemName.text = item.name
            Picasso.get().load(item.cover).into(binding.characterListItemCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}