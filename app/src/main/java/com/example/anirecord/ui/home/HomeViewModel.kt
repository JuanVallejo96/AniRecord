package com.example.anirecord.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.usecase.AddToListUseCase
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase,
    private val addToListUseCase: AddToListUseCase,
) : ViewModel() {
    private var page = 1
    private var continueLoading = true
    private val loadedItems: MutableList<ShowListItemModel> = mutableListOf()
    private val items = MutableLiveData<List<ShowListItemModel>>()

    val shows: LiveData<List<ShowListItemModel>> get() = items

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch {
            getPopularUseCase(
                Calendar.getInstance().get(Calendar.YEAR),
                Utils.getCurrentMediaSeason(),
                page,
            )?.let { (newItems, hasNextPage) ->
                continueLoading = hasNextPage
                loadedItems.addAll(newItems)
                items.postValue(loadedItems)
                page++
            }
        }
    }

    //TODO: si el toggle ta bien esto ya pa qué?
    fun addToList(listId: Int, showId: Int) = viewModelScope.launch {
        addToListUseCase(listId, showId)
    }
}