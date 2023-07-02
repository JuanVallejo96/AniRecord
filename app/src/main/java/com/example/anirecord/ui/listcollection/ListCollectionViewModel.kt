package com.example.anirecord.ui.listcollection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.usecase.CreateListUseCase
import com.example.anirecord.domain.usecase.DeleteListUseCase
import com.example.anirecord.domain.usecase.GetAllListsUseCase
import com.example.anirecord.domain.usecase.UpdateListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCollectionViewModel @Inject constructor(
    private val getLists: GetAllListsUseCase,
    private val createListUseCase: CreateListUseCase,
    private val updateListUseCase: UpdateListUseCase,
    private val deleteListUseCase: DeleteListUseCase,
) : ViewModel() {
    val lists: LiveData<List<ListCollectionItemModel>> get() = getLists()

    fun addList(name: String): Boolean {
        if (name.isBlank()) return false
        viewModelScope.launch(Dispatchers.IO) {
            createListUseCase(name)
        }
        return true
    }

    fun updateList(list: ListCollectionItemModel, newName: String): Boolean {
        if (newName.isBlank()) return false
        viewModelScope.launch(Dispatchers.IO) {
            list.name = newName
            updateListUseCase(list)
        }
        return true
    }

    fun deleteList(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteListUseCase(id)
    }
}
