package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetFavouritesUseCase {
    operator fun invoke(): LiveData<List<ShowListItemModel>>
}

class GetFavouritesUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : GetFavouritesUseCase {
    override fun invoke(): LiveData<List<ShowListItemModel>> = showRepository.getFavourites()
}