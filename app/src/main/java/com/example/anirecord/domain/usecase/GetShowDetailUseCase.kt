package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ShowRepository

interface GetShowDetailUseCase {
    suspend operator fun invoke(id: Int): ShowDetailModel
}

class GetShowDetailUseCaseImpl(private val showRepository: ShowRepository) : GetShowDetailUseCase {
    override suspend fun invoke(id: Int): ShowDetailModel {
        return showRepository.getShowById(id)
    }
}