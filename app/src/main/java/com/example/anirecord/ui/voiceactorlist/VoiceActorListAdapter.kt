package com.example.anirecord.ui.voiceactorlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anirecord.R
import com.example.anirecord.databinding.VoiceActorListItemBinding
import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.squareup.picasso.Picasso

class VoiceActorListAdapter(
    private val clickHandler: VoiceActorClickHandler
) : RecyclerView.Adapter<VoiceActorListAdapter.VoiceActorViewHolder>() {
    private val items = mutableListOf<ShowVoiceActorModel>()

    interface VoiceActorClickHandler {
        fun onVoiceActorClick(voiceActor: ShowVoiceActorModel)
    }

    class VoiceActorViewHolder(
        private val binding: VoiceActorListItemBinding,
        private val clickHandler: VoiceActorClickHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowVoiceActorModel) {
            binding.root.setOnClickListener {
                clickHandler.onVoiceActorClick(item)
            }
            binding.voiceActorListActorName.text = item.actorName
            Picasso.get().load(item.actorImage).into(binding.voiceActorListActorImage)
            binding.voiceActorListCharacterName.text =
                binding.root.context.getString(R.string.as_character_label, item.characterName)
            Picasso.get().load(item.characterImage).into(binding.voiceActorListCharacterImage)
            item.roleDetails?.let {
                binding.voiceActorListInfo.visibility = View.VISIBLE
                binding.voiceActorListInfo.text = String.format("(%s)", it)
            }
        }
    }

    fun update(actors: List<ShowVoiceActorModel>) {
        val initPosition = items.size
        items.clear()
        items.addAll(actors)
        notifyItemRangeInserted(initPosition, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceActorViewHolder {
        val binding = VoiceActorListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VoiceActorViewHolder(binding, clickHandler)
    }

    override fun onBindViewHolder(holder: VoiceActorViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}