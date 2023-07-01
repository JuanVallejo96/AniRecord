package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetShowDetailUseCase {
    suspend operator fun invoke(id: Int): ShowDetailModel
}

class GetShowDetailUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : GetShowDetailUseCase {
    override suspend fun invoke(id: Int): ShowDetailModel {
        return showRepository.findById(id)
    }
}