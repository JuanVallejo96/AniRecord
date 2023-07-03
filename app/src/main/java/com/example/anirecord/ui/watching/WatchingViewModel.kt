package com.example.anirecord.ui.watching

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.anirecord.domain.model.ShowProgressListItemModel
import com.example.anirecord.domain.usecase.GetWatchingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchingViewModel @Inject constructor(
    private val getWatchingUseCase: GetWatchingUseCase,
) : ViewModel() {
    val shows: LiveData<List<ShowProgressListItemModel>> get() = getWatchingUseCase()
}