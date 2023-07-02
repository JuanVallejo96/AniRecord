package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface AddToListUseCase {
    suspend operator fun invoke(listId: Int, showId: Int)
}

data class AddToListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : AddToListUseCase {
    override suspend fun invoke(listId: Int, showId: Int) = coroutineScope {
        listRepository.insertShowIntoList(listId, showId)
    }
}