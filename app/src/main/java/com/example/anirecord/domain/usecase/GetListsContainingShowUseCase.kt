package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetListsContainingShowUseCase {
    operator fun invoke(showId: Int): LiveData<List<ListCollectionItemModel>>
}

class GetListsContainingShowUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository,
) : GetListsContainingShowUseCase {
    override fun invoke(showId: Int): LiveData<List<ListCollectionItemModel>> {
        return showRepository.getShowWithLists(showId)
    }
}