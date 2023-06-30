package com.example.anirecord.domain.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.model.extensions.toModel
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.graphql.SearchQuery
import com.example.anirecord.graphql.SeasonPopularQuery
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.type.MediaSeason
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : ShowRepository {
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

    override suspend fun getShowById(id: Int): ShowDetailModel {
        return apolloClient.query(ShowDetailQuery(id)).execute().data?.Media!!.toModel()
    }

    override suspend fun getSearch(
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