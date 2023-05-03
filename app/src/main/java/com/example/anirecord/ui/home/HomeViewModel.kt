package com.example.anirecord.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.ui.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

class HomeViewModel : ViewModel(), KoinComponent {
    private val getPopularUseCase: GetPopularUseCase by inject()

    private var page = 1
    private var continueLoading = true
    private val items = MutableLiveData<List<ShowListItemModel>>(mutableListOf())
    val shows: LiveData<List<ShowListItemModel>> get() = items

    init {
        load()
    }

    fun load() {
        if (!continueLoading) return
        viewModelScope.launch(Dispatchers.IO) {
            getPopularUseCase(
                Calendar.getInstance().get(Calendar.YEAR),
                Utils.getCurrentMediaSeason(),
                page,
            )?.let { (newItems, hasNextPage) ->
                continueLoading = hasNextPage
                items.postValue(newItems)
                page++
            }
        }
    }
}