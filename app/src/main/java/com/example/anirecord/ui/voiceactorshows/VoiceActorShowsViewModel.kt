package com.example.anirecord.ui.voiceactorshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.example.anirecord.domain.usecase.GetVoiceActorShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoiceActorShowsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getVoiceActorsShowsUseCase: GetVoiceActorShowsUseCase,
) : ViewModel() {
    private val id = VoiceActorShowsFragmentArgs.fromSavedStateHandle(savedStateHandle).staffId

    private var page = 1
    private var continueLoading = true
    private val loadedItems: MutableList<VoiceActorShowsListItemModel> = mutableListOf()
    private val _shows = MutableLiveData<List<VoiceActorShowsListItemModel>>(loadedItems)

    val shows: LiveData<List<VoiceActorShowsListItemModel>> = _shows

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch(Dispatchers.IO) {
            getVoiceActorsShowsUseCase(id, page)?.let { (newItems, hasMoreItems) ->
                continueLoading = hasMoreItems
                loadedItems.addAll(newItems)
                _shows.postValue(loadedItems)
                page++
            }
        }
    }
}