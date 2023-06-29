package com.example.anirecord.ui.voiceactorlist

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
class VoiceActorListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShowVoiceActorsUseCase: GetVoiceActorsListUseCase
) : ViewModel() {
    private val showId = VoiceActorListFragmentArgs.fromSavedStateHandle(savedStateHandle).showId

    private var page = 1
    private var continueLoading = true
    private val loadedVoiceActors: MutableList<ShowVoiceActorModel> = mutableListOf()
    private val items = MutableLiveData<List<ShowVoiceActorModel>>(loadedVoiceActors)
    val voiceActors get(): LiveData<List<ShowVoiceActorModel>> = items

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch(Dispatchers.IO) {
            getShowVoiceActorsUseCase(showId, page)?.let { (items, hasMoreItems) ->
                continueLoading = hasMoreItems
                loadedVoiceActors.addAll(items)
                this@VoiceActorListViewModel.items.postValue(loadedVoiceActors)
                page++
            }
        }
    }
}