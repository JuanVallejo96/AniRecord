package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface DeleteShowFromListUseCase {
    suspend operator fun invoke(listId: Int, showId: Int)
}

data class DeleteShowFromListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : DeleteShowFromListUseCase {
    override suspend fun invoke(listId: Int, showId: Int) = coroutineScope {
        listRepository.deleteShowFromList(listId, showId)
    }
}