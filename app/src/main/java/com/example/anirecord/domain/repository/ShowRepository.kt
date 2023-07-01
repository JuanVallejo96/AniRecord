package com.example.anirecord.domain.repository

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.graphql.type.MediaSeason

interface ShowRepository {
    suspend fun findById(id: Int): ShowDetailModel

    suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>?

    fun getFavourites(): LiveData<List<ShowListItemModel>>

    suspend fun toggleFavourite(id: Int)

    suspend fun searchByQuery(
        query: String,
        page: Int
    ): Pair<List<ShowListItemModel>, Boolean>?
}