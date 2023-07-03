package com.example.anirecord.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.data.database.ShowDao
import com.example.anirecord.data.entity.ShowEntity
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.example.anirecord.domain.model.extensions.toListModel
import com.example.anirecord.domain.model.extensions.toModel
import com.example.anirecord.domain.model.extensions.toModelList
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.graphql.SearchQuery
import com.example.anirecord.graphql.SeasonPopularQuery
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.StaffShowsQuery
import com.example.anirecord.graphql.VoiceActorShowsQuery
import com.example.anirecord.graphql.type.MediaSeason
import com.example.anirecord.utils.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val showDao: ShowDao,
    private val appDispatchers: AppDispatchers,
) : ShowRepository {
    override fun findById(id: Int): LiveData<ShowDetailModel> = liveData {
        val showDetail = apolloClient.query(ShowDetailQuery(id)).execute().data?.Media!!.toModel()
        val data = showDao.findById(id)
        emitSource(
            data.map { showEntity ->
                showEntity?.let {
                    if (it.totalEpisodes != showDetail.episodes) {
                        it.totalEpisodes = showDetail.episodes
                        showDao.update(it)
                    }
                }
                showDetail.apply {
                    progress = showEntity?.progress
                    isPending = showEntity?.isPending ?: false
                    isFavourite = showEntity?.isFavourite ?: false
                }
            }
        )
    }

    override suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int
    ): Pair<List<ShowListItemModel>, Boolean>? = withContext(appDispatchers.IO) {
        val data = apolloClient.query(
            SeasonPopularQuery(page, year, season)
        ).execute().data?.Page ?: return@withContext null
        val items = data.media?.filterNotNull()?.map {
            it.showListItemFragment.toModel()
        } ?: listOf()
        return@withContext Pair(items, data.pageInfo?.hasNextPage ?: true)
    }

    override fun getFavourites(): LiveData<List<ShowListItemModel>> {
        return showDao.getFavourites().map { items ->
            items.map(ShowEntity::toListModel)
        }
    }

    override suspend fun toggleFavourite(showDetail: ShowDetailModel): Unit =
        withContext(appDispatchers.IO) {
            var show = showDao.getById(showDetail.id)
            if (show == null) {
                show = ShowEntity(
                    showId = showDetail.id,
                    name = showDetail.title!!,
                    cover = showDetail.cover!!,
                    isFavourite = true,
                )
                return@withContext showDao.insert(show)
            }

            show.isFavourite = !show.isFavourite
            showDao.update(show)
        }

    override fun getPending(): LiveData<List<ShowListItemModel>> {
        return showDao.getPending().map { items ->
            items.map(ShowEntity::toListModel)
        }
    }

    override suspend fun togglePending(showDetail: ShowDetailModel): Unit =
        withContext(appDispatchers.IO) {
            var show = showDao.getById(showDetail.id)
            if (show == null) {
                show = ShowEntity(
                    showId = showDetail.id,
                    name = showDetail.title!!,
                    cover = showDetail.cover!!,
                    isPending = true,
                )
                return@withContext showDao.insert(show)
            }

            show.isPending = !show.isPending
            showDao.update(show)
        }

    override fun getWatched(): LiveData<List<ShowListItemModel>> {
        return showDao.getWatched().map { items ->
            items.map(ShowEntity::toListModel)
        }
    }

    override suspend fun searchByQuery(
        query: String,
        page: Int
    ): Pair<List<ShowListItemModel>, Boolean>? = withContext(appDispatchers.IO) {
        val data = apolloClient.query(
            SearchQuery(query, page)
        ).execute().data?.Page ?: return@withContext null
        val items = data.media?.filterNotNull()?.map {
            it.showListItemFragment.toModel()
        } ?: listOf()
        return@withContext Pair(items, data.pageInfo?.hasNextPage ?: false)
    }

    override suspend fun getStaffShows(
        staffId: Int,
        page: Int
    ): Pair<List<StaffShowListItemModel>, Boolean>? = withContext(appDispatchers.IO) {
        val data = apolloClient.query(
            StaffShowsQuery(staffId, page)
        ).execute().data?.Staff?.staffMedia ?: return@withContext null
        val items = data.edges?.filterNotNull()?.map {
            it.toModel()
        } ?: listOf()
        return@withContext Pair(items, data.pageInfo?.hasNextPage ?: false)
    }

    override suspend fun getVoiceActorShows(
        staffId: Int,
        page: Int
    ): Pair<List<VoiceActorShowsListItemModel>, Boolean>? = withContext(appDispatchers.IO) {
        val data = apolloClient.query(
            VoiceActorShowsQuery(staffId, page)
        ).execute().data?.Staff?.characters ?: return@withContext null
        val items = data.toModelList()
        return@withContext Pair(items, data.pageInfo?.hasNextPage ?: false)
    }

    override fun getShowWithLists(showId: Int): LiveData<List<ListCollectionItemModel>> {
        return showDao.getShowWithLists(showId).map {
            it.flatMap {
                it.lists.map {
                    ListCollectionItemModel(
                        it.listId,
                        it.name,
                    )
                }
            }
        }
    }
}