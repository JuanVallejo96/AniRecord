package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ShowProgressListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetWatchingUseCase {
    operator fun invoke(): LiveData<List<ShowProgressListItemModel>>
}

class GetWatchingUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository,
) : GetWatchingUseCase {
    override fun invoke(): LiveData<List<ShowProgressListItemModel>> {
        return showRepository.getWatching()
    }
}