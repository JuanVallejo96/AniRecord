package com.example.anirecord.ui.staffshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.StaffDetailModel
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.domain.usecase.GetStaffDetailUseCase
import com.example.anirecord.domain.usecase.GetStaffShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffShowsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStaffShowsUseCase: GetStaffShowsUseCase,
    private val getStaffDetailUseCase: GetStaffDetailUseCase,
) : ViewModel() {
    private val id = StaffShowsFragmentArgs.fromSavedStateHandle(savedStateHandle).staffId

    private var page = 1
    private var continueLoading = true

    private var details: StaffDetailModel? = null
    private val loadedItems: MutableList<StaffShowListItemModel> = mutableListOf()
    private val _info = MutableLiveData<UiState>()

    val info: LiveData<UiState> = _info

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch {
            val pageLoad = async {
                getStaffShowsUseCase(id, page)
            }

            if (page == 1) {
                details = getStaffDetailUseCase(id)
            }

            pageLoad.await()?.let { (newItems, hasMoreItems) ->
                continueLoading = hasMoreItems
                loadedItems.addAll(newItems)
                page++
            }

            _info.postValue(UiState(details, loadedItems))
        }
    }

    data class UiState(val details: StaffDetailModel?, val shows: List<StaffShowListItemModel>)
}