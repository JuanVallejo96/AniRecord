package com.example.anirecord.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.SeasonPopularQuery
import com.example.anirecord.domain.model.SeriesModel
import com.example.anirecord.domain.model.asModel
import com.example.anirecord.type.MediaSeason

interface SeriesRepository {
    suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<SeriesModel>, Boolean>?
}

class SeriesRepositoryImpl(private val apolloClient: ApolloClient) : SeriesRepository {
    override suspend fun getPopularOnSeason(
        year: Int,
        season: MediaSeason,
        page: Int
    ): Pair<List<SeriesModel>, Boolean>? {
        val data = apolloClient.query(
            SeasonPopularQuery(page, year, season)
        ).execute().data?.Page ?: return null
        val items = data.media?.filterNotNull()?.map(SeasonPopularQuery.Medium::asModel)
            ?: listOf()
        return Pair(items, data.pageInfo?.hasNextPage ?: true)
    }
}