package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetShowDetailUseCase {
    operator fun invoke(id: Int): LiveData<ShowDetailModel>
}

class GetShowDetailUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : GetShowDetailUseCase {
    override fun invoke(id: Int): LiveData<ShowDetailModel> = liveData {
        emitSource(showRepository.findById(id))
    }
}