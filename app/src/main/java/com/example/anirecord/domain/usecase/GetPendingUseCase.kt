package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetPendingUseCase {
    operator fun invoke(): LiveData<List<ShowListItemModel>>
}

class GetPendingUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : GetPendingUseCase {
    override fun invoke(): LiveData<List<ShowListItemModel>> = showRepository.getPending()
}