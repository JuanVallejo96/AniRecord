package com.example.anirecord.domain.model

import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.type.MediaSeason
import com.example.anirecord.graphql.type.MediaStatus

data class ShowDetailModel(
    val id: Int,
    val title: String?,
    val cover: String?,
    val description: String?,
    val averageScore: Int?,
    val episodes: Int?,
    val season: MediaSeason?,
    val status: MediaStatus?,
    val year: Int?,
    val nextEpisode: ShowDetailQuery.NextAiringEpisode?,
    val characters: List<CharacterConnectionModel>
) {
    val ratingString: String
        get() = (this.averageScore?.let { (it / 10).toString() } ?: "??") + " / 10"
}