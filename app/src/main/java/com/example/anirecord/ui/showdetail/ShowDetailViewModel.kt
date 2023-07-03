package com.example.anirecord.ui.showdetail

import android.util.Log
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val getAllListsUseCase: GetAllListsUseCase,
    private val toggleListUseCase: ToggleListUseCase,
    private val getListsContainingShowUseCase: GetListsContainingShowUseCase,
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
                toggleListUseCase(listId, it)
            }
        }
    }

    fun hasLists(): Boolean = _listsLength > 0

    fun arrayEpisodes(): List<String> {
        Log.d("next", _show?.nextEpisode?.episode.toString())
        Log.d("episodes", _show?.episodes.toString())
        return (0..min(
            _show?.nextEpisode?.episode ?: Int.MAX_VALUE,
            _show?.episodes ?: Int.MAX_VALUE
        ))
            .map {
                if (it == 0) { //TODO: move to fragment to use @string
                    "Not watched"
                } else {
                    String.format("Episode %d", it)
                }
            }.toList()
    }
}