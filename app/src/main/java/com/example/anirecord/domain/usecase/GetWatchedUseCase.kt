package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetWatchedUseCase {
    operator fun invoke(): LiveData<List<ShowListItemModel>>
}

class GetWatchedUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository,
) : GetWatchedUseCase {
    override fun invoke(): LiveData<List<ShowListItemModel>> = showRepository.getWatched()
}