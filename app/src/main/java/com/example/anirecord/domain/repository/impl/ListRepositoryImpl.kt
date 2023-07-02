package com.example.anirecord.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.anirecord.data.database.ListDao
import com.example.anirecord.data.entity.ListEntity
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ListRepository
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listDao: ListDao
) : ListRepository {
    override fun getAll(): LiveData<List<ListCollectionItemModel>> {
        return listDao.getAll().map {
            it.map { list ->
                ListCollectionItemModel(list.listId, list.name)
            }
        }
    }

    override fun findWithShowsById(id: Int): LiveData<List<ShowListItemModel>> {
        return listDao.findWithShowsById(id).map {
            it.shows.map { showEntity ->
                ShowListItemModel(showEntity.showId, showEntity.name, showEntity.cover)
            }
        }
    }

    override suspend fun insert(name: String) {
        listDao.insert(ListEntity(0, name))
    }

    override suspend fun insertShowIntoList(listId: Int, showId: Int) {
        listDao.insertShowInList(listId, showId)
    }

    override suspend fun update(list: ListCollectionItemModel) {
        listDao.getById(list.id)?.let {
            it.name = list.name
            listDao.update(it)
        }
    }

    override suspend fun delete(id: Int) {
        listDao.getById(id)?.let {
            listDao.delete(it)
        }
    }

    override suspend fun deleteShowFromList(listId: Int, showId: Int) {
        listDao.deleteShowFromList(listId, showId)
    }
}