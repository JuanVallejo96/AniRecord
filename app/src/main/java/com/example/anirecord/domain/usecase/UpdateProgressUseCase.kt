package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface UpdateProgressUseCase {
    suspend operator fun invoke(showDetail: ShowDetailModel)
}

data class UpdateProgressUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : UpdateProgressUseCase {
    override suspend fun invoke(showDetail: ShowDetailModel) {
        showRepository.updateProgress(showDetail)
    }
}