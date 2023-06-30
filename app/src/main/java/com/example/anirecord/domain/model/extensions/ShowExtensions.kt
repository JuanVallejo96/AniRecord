package com.example.anirecord.domain.model.extensions

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.fragment.ShowListItemFragment

fun ShowDetailQuery.Media.toModel(): ShowDetailModel {
    return ShowDetailModel(
        id = id,
        title = title?.let { it.english ?: it.romaji },
        cover = coverImage?.extraLarge,
        description = description,
        averageScore = averageScore,
        episodes = episodes,
        season = season,
        status = status,
        year = seasonYear,
        nextEpisode = nextAiringEpisode,
        characters = characters?.toModelList() ?: listOf(),
        staff = staff?.toModelList() ?: listOf(),
    )
}

fun ShowListItemFragment.toModel(): ShowListItemModel {
    return ShowListItemModel(
        id = id,
        name = title?.let { it.english ?: it.romaji },
        cover = coverImage?.large,
    )
}