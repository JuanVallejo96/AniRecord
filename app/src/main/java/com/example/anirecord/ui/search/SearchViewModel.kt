package com.example.anirecord.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.usecase.SearchByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchByNameUseCase,
) : ViewModel() {
    private var page = 1
    private var continueLoading = true
    private lateinit var lastQuery: String
    private val loadedItems: MutableList<ShowListItemModel> = mutableListOf()
    private val _uiState = MutableLiveData<UiState>(UiState.Start)

    val uiState get(): LiveData<UiState> = _uiState

    fun search(query: String) {
        if (query.isBlank()) {
            _uiState.postValue(UiState.Start)
            return
        }

        page = 1
        lastQuery = query
        viewModelScope.launch {
            searchUseCase(
                query,
                page,
            )?.let { (newItems, hasNextPage) ->
                continueLoading = hasNextPage
                Log.d("CONTINUE", continueLoading.toString())
                loadedItems.clear()
                loadedItems.addAll(newItems)
                if (newItems.isEmpty()) {
                    _uiState.postValue(UiState.Empty)
                } else {
                    _uiState.postValue(UiState.Success(loadedItems, true))
                }
                page++
            }
        }
    }

    fun keepLoading() {
        if (!continueLoading) return
        viewModelScope.launch {
            searchUseCase(
                lastQuery,
                page,
            )?.let { (newItems, hasNextPage) ->
                continueLoading = hasNextPage
                loadedItems.addAll(newItems)
                _uiState.postValue(UiState.Success(loadedItems, false))
                page++
            }
        }
    }

    sealed class UiState {
        data class Success(val shows: List<ShowListItemModel>, val newSearch: Boolean) : UiState()
        object Start : UiState()
        object Empty : UiState()
    }
}