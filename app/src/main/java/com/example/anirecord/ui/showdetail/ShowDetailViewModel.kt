package com.example.anirecord.ui.showdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShowDetailViewModel(private val id: Int) : ViewModel(), KoinComponent {
    private val getShowDetailUseCase: GetShowDetailUseCase by inject()
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