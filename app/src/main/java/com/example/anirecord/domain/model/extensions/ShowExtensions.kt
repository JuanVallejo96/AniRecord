package com.example.anirecord.domain.model.extensions

import com.example.anirecord.data.entity.ShowEntity
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.model.ShowProgressListItemModel
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.StaffShowsQuery
import com.example.anirecord.graphql.VoiceActorShowsQuery
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

fun ShowEntity.toListModel(): ShowListItemModel {
    return ShowListItemModel(
        id = showId,
        name = name,
        cover = cover,
    )
}

fun ShowEntity.toProgressListModel(): ShowProgressListItemModel {
    return ShowProgressListItemModel(
        id = showId,
        name = name,
        cover = cover,
        progress = progress ?: 0,
        totalEpisodes = totalEpisodes,
    )
}

fun ShowListItemFragment.toModel(): ShowListItemModel {
    return ShowListItemModel(
        id = id,
        name = title?.let { it.english ?: it.romaji },
        cover = coverImage?.large,
    )
}

fun StaffShowsQuery.Edge.toModel(): StaffShowListItemModel {
    val show = node!!.showListItemFragment
    return StaffShowListItemModel(
        id = show.id,
        name = show.title?.let { it.english ?: it.romaji },
        cover = show.coverImage?.large,
        staffRole = staffRole,
    )
}

fun VoiceActorShowsQuery.Characters.toModelList(): List<VoiceActorShowsListItemModel> {
    return edges?.filterNotNull()?.flatMap {
        it.media?.filterNotNull()?.map { media ->
            VoiceActorShowsListItemModel(
                mediaId = media.showListItemFragment.id,
                mediaTitle = media.showListItemFragment.title!!.let { it.romaji ?: it.english!! },
                mediaCover = media.showListItemFragment.coverImage!!.large!!,
                characterName = it.node!!.name!!.full!!,
                characterCover = it.node.image!!.large!!,
            )
        } ?: listOf()
    } ?: listOf()
}