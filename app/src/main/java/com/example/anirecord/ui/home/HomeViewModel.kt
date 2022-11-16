package com.example.anirecord.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.SeriesModel
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.ui.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class HomeViewModel : ViewModel(), KoinComponent {
    private val getPopularUseCase: GetPopularUseCase by inject()

    private var page = 1
    private var continueLoading = true
    private val items = MutableLiveData<List<SeriesModel>>(mutableListOf())
    val series: LiveData<List<SeriesModel>> get() = items

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
                viewModelScope.launch(Dispatchers.Main) {
                    items.value = newItems
                    page++
                }
            }
        }
    }
}