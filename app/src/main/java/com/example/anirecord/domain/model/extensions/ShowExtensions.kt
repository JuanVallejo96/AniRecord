package com.example.anirecord.domain.model.extensions

import com.example.anirecord.SeasonPopularQuery
import com.example.anirecord.ShowDetailQuery
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel

fun ShowDetailQuery.Media.toModel(): ShowDetailModel {
    return ShowDetailModel(
        id = id,
        cover = coverImage?.extraLarge
    )
}

fun SeasonPopularQuery.Medium.toModel(): ShowListItemModel {
    return ShowListItemModel(
        id = id,
        name = title?.let { it.english ?: it.romaji },
        cover = coverImage?.large,
    )
}