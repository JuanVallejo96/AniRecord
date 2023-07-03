package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ShowRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface TogglePendingUseCase {
    suspend operator fun invoke(showDetail: ShowDetailModel)
}

class TogglePendingUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository,
) : TogglePendingUseCase {
    override suspend fun invoke(showDetail: ShowDetailModel) = coroutineScope {
        showRepository.togglePending(showDetail)
    }
}