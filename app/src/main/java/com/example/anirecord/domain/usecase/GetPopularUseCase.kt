package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.SeriesModel
import com.example.anirecord.domain.repository.SeriesRepository
import com.example.anirecord.type.MediaSeason

interface GetPopularUseCase {
    suspend operator fun invoke(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<SeriesModel>, Boolean>?
}

class GetPopularUseCaseImpl(private val seriesRepository: SeriesRepository) : GetPopularUseCase {
    override suspend fun invoke(
        year: Int,
        season: MediaSeason,
        page: Int,
    ): Pair<List<SeriesModel>, Boolean>? =
        seriesRepository.getPopularOnSeason(year, season, page)
}