package com.example.anirecord.domain.model.extensions

import com.example.anirecord.domain.model.CharacterConnectionModel
import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.ShowVoiceActorsQuery

fun ShowDetailQuery.Characters.toModelList(): List<CharacterConnectionModel> {
    return edges?.filterNotNull()?.map {
        CharacterConnectionModel(
            actorId = it.voiceActorRoles?.firstOrNull()?.voiceActor?.id,
            actorName = it.voiceActorRoles?.firstOrNull()?.voiceActor?.name?.full,
            characterName = it.node!!.name!!.full!!,
            cover = it.node.image?.large
        )
    } ?: listOf()
}

fun ShowVoiceActorsQuery.Characters.toModelList(): List<ShowVoiceActorModel> {
    return edges?.filterNotNull()?.flatMap { character ->
        character.voiceActorRoles?.filterNotNull()?.map { actor ->
            ShowVoiceActorModel(
                actorId = actor.voiceActor!!.id,
                actorName = actor.voiceActor.name!!.full!!,
                actorImage = actor.voiceActor.image!!.large!!,
                characterName = character.node!!.name!!.full!!,
                characterImage = character.node.image!!.large!!,
                roleDetails = actor.roleNotes,
            )
        } ?: listOf()
    } ?: listOf()
}