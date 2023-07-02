package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.graphql.type.MediaSeason
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface GetPopularUseCase {
    suspend operator fun invoke(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>?
}

class GetPopularUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : GetPopularUseCase {
    override suspend fun invoke(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>? = coroutineScope {
        showRepository.getPopularOnSeason(year, season, page)
    }
}