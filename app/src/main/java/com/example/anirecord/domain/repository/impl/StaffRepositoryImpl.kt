package com.example.anirecord.domain.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.domain.model.extensions.toModelList
import com.example.anirecord.domain.repository.StaffRepository
import com.example.anirecord.graphql.ShowVoiceActorsQuery
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : StaffRepository {
    override suspend fun getShowVoiceActors(
        showId: Int,
        page: Int
    ): Pair<List<ShowVoiceActorModel>, Boolean>? {
        val characters = apolloClient.query(
            ShowVoiceActorsQuery(showId, page)
        ).execute().data?.Media?.characters ?: return null
        return Pair(characters.toModelList(), characters.pageInfo?.hasNextPage ?: true)
    }
}