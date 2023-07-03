package com.example.anirecord.domain.repository

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.example.anirecord.graphql.type.MediaSeason

interface ShowRepository {
    fun findById(id: Int): LiveData<ShowDetailModel>

    suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>?

    fun getFavourites(): LiveData<List<ShowListItemModel>>

    suspend fun toggleFavourite(showDetail: ShowDetailModel)

    suspend fun searchByQuery(
        query: String,
        page: Int
    ): Pair<List<ShowListItemModel>, Boolean>?

    suspend fun getStaffShows(
        staffId: Int,
        page: Int,
    ): Pair<List<StaffShowListItemModel>, Boolean>?

    suspend fun getVoiceActorShows(
        staffId: Int,
        page: Int,
    ): Pair<List<VoiceActorShowsListItemModel>, Boolean>?

    fun getShowWithLists(showId: Int): LiveData<List<ListCollectionItemModel>>
}