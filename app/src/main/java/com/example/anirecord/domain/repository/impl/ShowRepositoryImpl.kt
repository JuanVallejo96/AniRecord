package com.example.anirecord.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val showDao: ShowDao,
) : ShowRepository {
    override fun findById(id: Int): LiveData<ShowDetailModel> = liveData {
        val showDetail = apolloClient.query(ShowDetailQuery(id)).execute().data?.Media!!.toModel()
        val data = showDao.findById(id)
        emitSource(
            data.map { showEntity ->
                showDetail.apply {
                    isFavourite = showEntity?.isFavourite ?: false
                }
            }
        )
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

    override suspend fun toggleFavourite(showDetail: ShowDetailModel) {
        var show = showDao.getById(showDetail.id)
        if (show == null) {
            show = ShowEntity(
                showId = showDetail.id,
                name = showDetail.title!!,
                cover = showDetail.cover!!,
                progress = 0,
                isFavourite = true,
            )
            return showDao.insert(show)
        }

        show.isFavourite = !show.isFavourite
        showDao.update(show)
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