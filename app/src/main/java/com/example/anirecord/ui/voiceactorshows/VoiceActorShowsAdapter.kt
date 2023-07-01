package com.example.anirecord.ui.voiceactorshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.databinding.VoiceActorShowsListItemBinding
import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.squareup.picasso.Picasso

class VoiceActorShowsAdapter(
    private val clickHandler: VoiceActorShowClickHandler
) : RecyclerView.Adapter<VoiceActorShowsAdapter.VoiceActorShowViewHolder>() {
    private val items = mutableListOf<VoiceActorShowsListItemModel>()

    interface VoiceActorShowClickHandler {
        fun onShowClick(show: VoiceActorShowsListItemModel)
    }

    class VoiceActorShowViewHolder(
        private val binding: VoiceActorShowsListItemBinding,
        private val clickHandler: VoiceActorShowClickHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VoiceActorShowsListItemModel) {
            binding.root.setOnClickListener {
                clickHandler.onShowClick(item)
            }
            binding.voiceActorShowsListShowTitle.text = item.mediaTitle
            Picasso.get().load(item.mediaCover).into(binding.voiceActorShowsListShowCover)
            binding.voiceActorShowsListCharacterName.text = item.characterName
            Picasso.get().load(item.characterCover).into(binding.voiceActorShowsListCharacterCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceActorShowViewHolder {
        val binding = VoiceActorShowsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return VoiceActorShowViewHolder(binding, clickHandler)
    }

    fun update(shows: List<VoiceActorShowsListItemModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(shows)
        notifyItemRangeInserted(initPosition, items.size)
    }


    override fun onBindViewHolder(holder: VoiceActorShowViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}