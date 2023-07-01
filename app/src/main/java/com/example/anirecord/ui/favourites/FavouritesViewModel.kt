package com.example.anirecord.ui.favourites

import androidx.lifecycle.ViewModel
import com.example.anirecord.domain.usecase.GetFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    getFavouritesUseCase: GetFavouritesUseCase
) : ViewModel() {
    val shows = getFavouritesUseCase()
}