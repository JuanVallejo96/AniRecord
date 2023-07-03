package com.example.anirecord.ui.watched

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.usecase.GetWatchedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchedViewModel @Inject constructor(
    private val getWatchedUseCase: GetWatchedUseCase,
) : ViewModel() {
    val shows: LiveData<List<ShowListItemModel>> get() = getWatchedUseCase()
}