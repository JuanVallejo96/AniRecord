package com.example.anirecord.domain.model

import com.example.anirecord.ShowDetailQuery
import com.example.anirecord.type.MediaSeason

data class ShowDetailModel(
    val id: Int,
    val title: String?,
    val cover: String?,
    val description: String?,
    val averageScore: Int?,
    val episodes: Int?,
    val season: MediaSeason?,
    val year: Int?,
    val nextEpisode: ShowDetailQuery.NextAiringEpisode?
) {
    val ratingString: String
        get() = (this.averageScore?.let { (it / 10).toString() } ?: "??") + " / 10"
}