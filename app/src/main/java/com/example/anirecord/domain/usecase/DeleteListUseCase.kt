package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import javax.inject.Inject

interface DeleteListUseCase {
    suspend operator fun invoke(id: Int)
}

class DeleteListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : DeleteListUseCase {
    override suspend fun invoke(id: Int) {
        listRepository.delete(id)
    }
}