package com.example.anirecord.ui.showdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
) : ViewModel() {
    private val id: Int = ShowDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).showId
    private var _show: ShowDetailModel? = null
    val show: LiveData<ShowDetailModel>
        get() = getShowDetailUseCase(id).map {
            _show = it
            it
        }

    fun toggleFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            _show?.let {
                toggleFavouriteUseCase(it)
            }
        }
    }
}