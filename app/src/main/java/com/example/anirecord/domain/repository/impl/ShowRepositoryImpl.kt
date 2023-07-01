package com.example.anirecord.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.data.database.ShowDao
import com.example.anirecord.data.entity.ShowEntity
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.model.extensions.toListModel
import com.example.anirecord.domain.model.extensions.toModel
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.graphql.SearchQuery
import com.example.anirecord.graphql.SeasonPopularQuery
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.type.MediaSeason
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val showDao: ShowDao,
) : ShowRepository {
    override suspend fun findById(id: Int): ShowDetailModel = coroutineScope {
        val favourite = async {
            showDao.findById(id).value?.isFavourite ?: false
        }

        apolloClient.query(ShowDetailQuery(id)).execute().data?.Media!!.toModel().apply {
            isFavourite = favourite.await()
        }
    }

    override suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int
    ): Pair<List<ShowListItemModel>, Boolean>? {
        val data = apolloClient.query(
            SeasonPopularQuery(page, year, season)
        ).execute().data?.Page ?: return null
        val items = data.media?.filterNotNull()?.map {
            it.showListItemFragment.toModel()
        } ?: listOf()
        return Pair(items, data.pageInfo?.hasNextPage ?: true)
    }

    override fun getFavourites(): LiveData<List<ShowListItemModel>> {
        return showDao.getFavourites().map { items ->
            items.map(ShowEntity::toListModel)
        }
    }

    override suspend fun toggleFavourite(id: Int) {
        showDao.findById(id).value?.let { item ->
            item.isFavourite = !item.isFavourite
        }
    }

    override suspend fun searchByQuery(
        query: String,
        page: Int
    ): Pair<List<ShowListItemModel>, Boolean>? {
        val data = apolloClient.query(
            SearchQuery(query, page)
        ).execute().data?.Page ?: return null
        val items = data.media?.filterNotNull()?.map {
            it.showListItemFragment.toModel()
        } ?: listOf()
        return Pair(items, data.pageInfo?.hasNextPage ?: false)
    }
}