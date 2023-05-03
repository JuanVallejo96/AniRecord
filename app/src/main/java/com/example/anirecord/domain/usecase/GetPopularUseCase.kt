package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.type.MediaSeason

interface GetPopularUseCase {
    suspend operator fun invoke(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>?
}

class GetPopularUseCaseImpl(private val showRepository: ShowRepository) : GetPopularUseCase {
    override suspend fun invoke(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>? =
        showRepository.getPopularOnSeason(year, season, page)
}