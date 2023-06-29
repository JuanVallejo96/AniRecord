package com.example.anirecord.ui.showdetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.R
import com.example.anirecord.databinding.ShowDetailCharacterListItemBinding
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
        private val binding: ShowDetailCharacterListItemBinding,
        private val clickHandler: CharacterClickHandler,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterConnectionModel) {
            binding.root.setOnClickListener { }
            binding.characterListItemSubInfo.visibility = View.GONE

            item.actorName?.let {
                binding.characterListItemSubInfo.visibility = View.VISIBLE
                binding.characterListItemSubInfo.text =
                    itemView.context.getString(R.string.by_actor_label, item.actorName)
                binding.root.setOnClickListener {
                    clickHandler.onCharacterClick(item)
                }
            }
            binding.characterListItemMainInfo.text = item.characterName
            Picasso.get().load(item.cover).into(binding.characterListItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ShowDetailCharacterListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}