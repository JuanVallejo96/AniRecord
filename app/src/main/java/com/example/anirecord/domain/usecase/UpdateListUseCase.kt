package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ListRepository
import javax.inject.Inject

interface UpdateListUseCase {
    suspend operator fun invoke(list: ListCollectionItemModel)
}

class UpdateListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : UpdateListUseCase {
    override suspend fun invoke(list: ListCollectionItemModel) {
        listRepository.update(list)
    }
}