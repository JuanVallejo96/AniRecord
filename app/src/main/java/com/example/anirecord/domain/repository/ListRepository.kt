package com.example.anirecord.domain.repository

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowListItemModel

interface ListRepository {
    fun getAll(): LiveData<List<ListCollectionItemModel>>
    fun findShowsById(id: Int): LiveData<List<ShowListItemModel>>
    suspend fun insert(name: String)
    suspend fun insertShowIntoList(listId: Int, showId: Int)
    suspend fun update(list: ListCollectionItemModel)
    suspend fun delete(id: Int)
    suspend fun deleteShowFromList(listId: Int, showId: Int)
    suspend fun toggleList(listId: Int, showId: Int)
}