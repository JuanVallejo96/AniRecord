package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface ToggleFavouriteUseCase {
    suspend operator fun invoke(showDetail: ShowDetailModel)
}

class ToggleFavouriteUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository,
) : ToggleFavouriteUseCase {
    override suspend fun invoke(showDetail: ShowDetailModel) {
        showRepository.toggleFavourite(showDetail)
    }
}