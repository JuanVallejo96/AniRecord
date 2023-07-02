package com.example.anirecord.ui.listdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.usecase.DeleteShowFromListUseCase
import com.example.anirecord.domain.usecase.GetShowsOnListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getShows: GetShowsOnListUseCase,
    private val deleteShowFromListUseCase: DeleteShowFromListUseCase
) : ViewModel() {
    private val idList = ListDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).listId
    val shows: LiveData<List<ShowListItemModel>> get() = getShows(idList)

    fun deleteShowFromList(showId: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteShowFromListUseCase(idList, showId)
    }
}