package com.example.anirecord.ui.showdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.usecase.GetAllListsUseCase
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCase
import com.example.anirecord.domain.usecase.ToggleListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val getAllListsUseCase: GetAllListsUseCase,
    private val toggleListUseCase: ToggleListUseCase
) : ViewModel() {
    private val id: Int = ShowDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).showId
    private var _show: ShowDetailModel? = null
    val show: LiveData<ShowDetailModel>
        get() = getShowDetailUseCase(id).map {
            _show = it
            it
        }
    val lists: LiveData<List<ListCollectionItemModel>> get() = getAllListsUseCase()

    fun toggleFavourite() {
        viewModelScope.launch {
            _show?.let {
                toggleFavouriteUseCase(it)
            }
        }
    }

    fun toggleList(listId: Int) {
        viewModelScope.launch {
            _show?.let {
                toggleListUseCase(listId, it.id)
            }
        }
    }
}