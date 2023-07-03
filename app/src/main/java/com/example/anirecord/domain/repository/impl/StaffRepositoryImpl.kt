package com.example.anirecord.domain.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.domain.model.StaffDetailModel
import com.example.anirecord.domain.model.extensions.toModel
import com.example.anirecord.domain.model.extensions.toModelList
import com.example.anirecord.domain.repository.StaffRepository
import com.example.anirecord.graphql.ShowStaffQuery
import com.example.anirecord.graphql.ShowVoiceActorsQuery
import com.example.anirecord.graphql.StaffDetailsQuery
import com.example.anirecord.utils.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StaffRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val appDispatchers: AppDispatchers,
) : StaffRepository {
    override suspend fun getStaffDetails(staffId: Int): StaffDetailModel? =
        withContext(appDispatchers.IO) {
            val data = apolloClient.query(
                StaffDetailsQuery(staffId)
            ).execute().data?.Staff ?: return@withContext null
            return@withContext data.toModel()
        }

    override suspend fun getShowVoiceActors(
        showId: Int,
        page: Int
    ): Pair<List<ShowVoiceActorModel>, Boolean>? = withContext(appDispatchers.IO) {
        val characters = apolloClient.query(
            ShowVoiceActorsQuery(showId, page)
        ).execute().data?.Media?.characters ?: return@withContext null
        return@withContext Pair(characters.toModelList(), characters.pageInfo?.hasNextPage ?: true)
    }

    override suspend fun getShowStaff(
        showId: Int,
        page: Int
    ): Pair<List<ShowStaffListItemModel>, Boolean>? = withContext(appDispatchers.IO) {
        val characters = apolloClient.query(
            ShowStaffQuery(showId, page)
        ).execute().data?.Media?.staff ?: return@withContext null
        return@withContext Pair(characters.toModelList(), characters.pageInfo?.hasNextPage ?: true)
    }
}