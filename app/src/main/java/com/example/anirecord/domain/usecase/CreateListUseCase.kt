package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface CreateListUseCase {
    suspend operator fun invoke(name: String)
}

class CreateListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : CreateListUseCase {
    override suspend fun invoke(name: String) = coroutineScope {
        listRepository.insert(name)
    }
}