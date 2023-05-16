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
    private val _uiState = MutableLiveData<ShowDetailUiState>(ShowDetailUiState.Loading)

    val uiState get(): LiveData<ShowDetailUiState> = _uiState

    init {
        load()
    }

    private fun load() = viewModelScope.launch(Dispatchers.IO) {
        val show = getShowDetailUseCase.invoke(id)
        _uiState.postValue(ShowDetailUiState.Success(show))
    }
}

sealed class ShowDetailUiState {
    data class Success(val show: ShowDetailModel) : ShowDetailUiState()
    object Loading : ShowDetailUiState()
}