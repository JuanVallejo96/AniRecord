package com.example.anirecord.ui.stafflist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.usecase.GetStaffListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowStaffUseCase: GetStaffListUseCase
) : ViewModel() {
    private val showId = StaffListFragmentArgs.fromSavedStateHandle(savedStateHandle).showId

    private var page = 1
    private var continueLoading = true
    private val loadedItems: MutableList<ShowStaffListItemModel> = mutableListOf()
    private val items = MutableLiveData<List<ShowStaffListItemModel>>(loadedItems)
    val staff: LiveData<List<ShowStaffListItemModel>> get() = items

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch {
            getShowStaffUseCase(showId, page)?.let { (newItems, hasMoreItems) ->
                loadedItems.addAll(newItems)
                items.postValue(loadedItems)
                continueLoading = hasMoreItems
                page++
            }
        }
    }
}