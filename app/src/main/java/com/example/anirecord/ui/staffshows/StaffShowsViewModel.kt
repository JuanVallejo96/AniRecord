package com.example.anirecord.ui.staffshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.domain.usecase.GetStaffShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffShowsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStaffShowsUseCase: GetStaffShowsUseCase,
) : ViewModel() {
    private val id = StaffShowsFragmentArgs.fromSavedStateHandle(savedStateHandle).staffId

    private var page = 1
    private var continueLoading = true
    private val loadedItems: MutableList<StaffShowListItemModel> = mutableListOf()
    private val _shows = MutableLiveData<List<StaffShowListItemModel>>(loadedItems)

    val shows: LiveData<List<StaffShowListItemModel>> = _shows

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch(Dispatchers.IO) {
            getStaffShowsUseCase(id, page)?.let { (newItems, hasMoreItems) ->
                continueLoading = hasMoreItems
                loadedItems.addAll(newItems)
                _shows.postValue(loadedItems)
                page++
            }
        }
    }
}