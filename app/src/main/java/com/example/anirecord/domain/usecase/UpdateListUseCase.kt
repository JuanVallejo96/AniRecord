package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ListRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface UpdateListUseCase {
    suspend operator fun invoke(list: ListCollectionItemModel)
}

class UpdateListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : UpdateListUseCase {
    override suspend fun invoke(list: ListCollectionItemModel) = coroutineScope {
        listRepository.update(list)
    }
}