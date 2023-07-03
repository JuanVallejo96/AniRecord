package com.example.anirecord.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.anirecord.data.database.ListDao
import com.example.anirecord.data.database.ShowDao
import com.example.anirecord.data.entity.ListEntity
import com.example.anirecord.data.entity.ShowEntity
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ListRepository
import com.example.anirecord.utils.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listDao: ListDao,
    private val showDao: ShowDao,
    private val appDispatchers: AppDispatchers,
) : ListRepository {
    override fun getAll(): LiveData<List<ListCollectionItemModel>> {
        return listDao.getAll().map {
            it.map { list ->
                ListCollectionItemModel(list.listId, list.name)
            }
        }
    }

    override fun findShowsById(id: Int): LiveData<List<ShowListItemModel>> {
        return listDao.findWithShowsById(id).map {
            it.shows.map { showEntity ->
                ShowListItemModel(showEntity.showId, showEntity.name, showEntity.cover)
            }
        }
    }

    override suspend fun insert(name: String) = withContext(appDispatchers.IO) {
        listDao.insert(ListEntity(0, name))
    }

    override suspend fun insertShowIntoList(listId: Int, showId: Int) =
        withContext(appDispatchers.IO) {
            listDao.insertShowInList(listId, showId)
        }

    override suspend fun update(list: ListCollectionItemModel): Unit =
        withContext(appDispatchers.IO) {
            listDao.getById(list.id)?.let {
                it.name = list.name
                listDao.update(it)
            }
        }

    override suspend fun delete(id: Int): Unit = withContext(appDispatchers.IO) {
        listDao.getById(id)?.let {
            listDao.delete(it)
        }
    }

    override suspend fun deleteShowFromList(listId: Int, showId: Int): Unit =
        withContext(appDispatchers.IO) {
            listDao.deleteShowFromList(listId, showId)
        }

    override suspend fun toggleList(listId: Int, showDetail: ShowDetailModel): Unit =
        withContext(appDispatchers.IO) {
            var show = showDao.getById(showDetail.id)
            if (show == null) {
                show = ShowEntity(
                    showId = showDetail.id,
                    name = showDetail.title!!,
                    cover = showDetail.cover!!,
                    progress = null,
                    isFavourite = false,
                )
                showDao.insert(show)
            }
            if (listDao.existsShowInList(listId, show.showId)) {
                deleteShowFromList(listId, show.showId)
            } else {
                insertShowIntoList(listId, show.showId)
            }
        }
}