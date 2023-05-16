package com.example.anirecord.ui.showdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowDetailUseCase: GetShowDetailUseCase,
) : ViewModel() {
    private val id: Int = ShowDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).showId
    private val _uiState = MutableLiveData<UiState>(UiState.Loading)

    val uiState get(): LiveData<UiState> = _uiState

    init {
        load()
    }

    private fun load() = viewModelScope.launch(Dispatchers.IO) {
        val show = getShowDetailUseCase(id)
        _uiState.postValue(UiState.Success(show))
    }

    sealed class UiState {
        data class Success(val show: ShowDetailModel) : UiState()
        object Loading : UiState()
    }
}