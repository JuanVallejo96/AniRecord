package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface ToggleListUseCase {
    suspend operator fun invoke(listId: Int, showDetail: ShowDetailModel)
}

data class ToggleListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : ToggleListUseCase {
    override suspend fun invoke(listId: Int, showDetail: ShowDetailModel) = coroutineScope {
        listRepository.toggleList(listId, showDetail)
    }
}