package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ListRepository
import javax.inject.Inject

interface GetAllListsUseCase {
    operator fun invoke(): LiveData<List<ListCollectionItemModel>>
}

class GetAllListsUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : GetAllListsUseCase {
    override fun invoke(): LiveData<List<ListCollectionItemModel>> {
        return listRepository.getAll()
    }
}