package com.example.anirecord.domain.repository

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.type.MediaSeason

interface ShowRepository {
    suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>?

    suspend fun getShowById(id: Int): ShowDetailModel
}