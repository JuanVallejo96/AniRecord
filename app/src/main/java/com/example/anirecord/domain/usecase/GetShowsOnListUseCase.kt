package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ListRepository
import javax.inject.Inject

interface GetShowsOnListUseCase {
    operator fun invoke(idList: Int): LiveData<List<ShowListItemModel>>
}

class GetShowsOnListUseCaseImpl @Inject constructor(
    private val listRepository: ListRepository
) : GetShowsOnListUseCase {
    override fun invoke(idList: Int): LiveData<List<ShowListItemModel>> {
        return listRepository.findShowsById(idList)
    }
}