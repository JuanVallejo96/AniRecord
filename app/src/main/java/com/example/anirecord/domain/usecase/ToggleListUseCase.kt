package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository

interface ToggleListUseCase {
    suspend operator fun invoke(listId: Int, showId: Int)
}

data class ToggleListUseCaseImpl(
    private val listRepository: ListRepository
) : ToggleListUseCase {
    override suspend fun invoke(listId: Int, showId: Int) {
        listRepository.toggleList(listId, showId)
    }
}