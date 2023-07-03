package com.example.anirecord.ui.showdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.usecase.GetAllListsUseCase
import com.example.anirecord.domain.usecase.GetListsContainingShowUseCase
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCase
import com.example.anirecord.domain.usecase.ToggleListUseCase
import com.example.anirecord.domain.usecase.TogglePendingUseCase
import com.example.anirecord.domain.usecase.UpdateProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val getAllListsUseCase: GetAllListsUseCase,
    private val toggleListUseCase: ToggleListUseCase,
    private val getListsContainingShowUseCase: GetListsContainingShowUseCase,
    private val togglePending: TogglePendingUseCase,
    private val updateProgress: UpdateProgressUseCase
) : ViewModel() {
    private val id: Int = ShowDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).showId
    private var _show: ShowDetailModel? = null
    private var _listsLength: Int = 0

    val show: LiveData<ShowDetailModel>
        get() = getShowDetailUseCase(id).map {
            _show = it
            it
        }

    val lists: LiveData<List<Pair<ListCollectionItemModel, Boolean>>>
        get() = show.switchMap {
            getAllListsUseCase().switchMap { allLists ->
                getListsContainingShowUseCase(id).map { listsWithShow ->
                    val idsOfListsWithShow = listsWithShow.map(ListCollectionItemModel::id).toSet()
                    _listsLength = allLists.size
                    allLists.map {
                        Pair(it, idsOfListsWithShow.contains(it.id))
                    }
                }
            }
        }

    fun toggleFavourite() = viewModelScope.launch {
        _show?.let {
            toggleFavouriteUseCase(it)
        }
    }

    fun togglePending() {
        viewModelScope.launch {
            _show?.let {
                togglePending(it)
            }
        }
    }

    fun toggleList(listId: Int) = viewModelScope.launch {
        _show?.let {
            toggleListUseCase(listId, it)
        }
    }

    fun updateProgress(episode: Int?) {
        viewModelScope.launch {
            _show?.let {
                it.progress = episode
                updateProgress(it)
            }
        }
    }

    fun hasLists(): Boolean = _listsLength > 0
}