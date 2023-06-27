package com.example.anirecord.ui.stafflist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.domain.usecase.GetVoiceActorsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowVoiceActorsUseCase: GetVoiceActorsListUseCase
) : ViewModel() {
    private val showId = StaffListFragmentArgs.fromSavedStateHandle(savedStateHandle).showId
    private val staffKind = StaffListFragmentArgs.fromSavedStateHandle(savedStateHandle).staffKind

    private val loadedVoiceActors: MutableList<ShowVoiceActorModel> = mutableListOf()

    private var page = 1
    private var continueLoading = true
    private val _uiState = MutableLiveData(
        when (staffKind) {
            StaffKind.VoiceActors -> UiState.VoiceActors(listOf())
            StaffKind.Staff -> UiState.Staff(listOf())
        }
    )

    val uiState get(): LiveData<UiState> = _uiState

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch(Dispatchers.IO) {
            continueLoading = when (staffKind) {
                StaffKind.VoiceActors -> loadVoiceActors()
                StaffKind.Staff -> loadStaff()
            }
            if (continueLoading) {
                page++
            }
        }
    }

    private suspend fun loadVoiceActors(): Boolean {
        return getShowVoiceActorsUseCase(showId, page)?.let { (items, hasMoreItems) ->
            loadedVoiceActors.addAll(items)
            _uiState.postValue(UiState.VoiceActors(loadedVoiceActors))
            hasMoreItems
        } ?: false
    }

    private suspend fun loadStaff(): Boolean {
        // TODO
        return false
    }

    sealed class UiState {
        class VoiceActors(val items: List<ShowVoiceActorModel>) : UiState()
        class Staff(val items: List<Any>) : UiState()
    }
}