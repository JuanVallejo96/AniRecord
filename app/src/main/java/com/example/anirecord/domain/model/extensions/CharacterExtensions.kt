package com.example.anirecord.domain.model.extensions

import com.example.anirecord.domain.model.CharacterConnectionModel
import com.example.anirecord.graphql.ShowDetailQuery

fun ShowDetailQuery.Characters.toModelList(): List<CharacterConnectionModel> {
    return edges?.filterNotNull()?.mapNotNull(ShowDetailQuery.Edge::node)?.map {
        CharacterConnectionModel(
            name = it.name!!.full!!,
            cover = it.image?.large
        )
    } ?: listOf()
}