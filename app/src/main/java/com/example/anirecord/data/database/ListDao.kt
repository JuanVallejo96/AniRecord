package com.example.anirecord.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.anirecord.Constants
import com.example.anirecord.data.entity.ListEntity
import com.example.anirecord.data.entity.ListWithShows

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg list: ListEntity)

    @Query("INSERT INTO ${Constants.DB_LIST_SHOW_XREFF_TABLE_NAME} (listId, showId) VALUES (:listId, :showId)")
    fun insertShowInList(listId: Int, showId: Int)

    @Query("SELECT * FROM ${Constants.DB_LIST_TABLE_NAME} WHERE listId = :id")
    fun findById(id: Int): LiveData<ListEntity>

    @Query("SELECT * FROM ${Constants.DB_LIST_TABLE_NAME} WHERE listId = :id")
    fun getById(id: Int): ListEntity?

    @Query("SELECT * FROM ${Constants.DB_LIST_TABLE_NAME} WHERE listId = :id")
    @Transaction
    fun findWithShowsById(id: Int): LiveData<ListWithShows>

    @Query("SELECT * FROM ${Constants.DB_LIST_TABLE_NAME}")
    fun getAll(): LiveData<List<ListEntity>>

    @Update
    fun update(list: ListEntity)

    @Delete
    fun delete(vararg lists: ListEntity)

    @Query("DELETE FROM ${Constants.DB_LIST_SHOW_XREFF_TABLE_NAME} WHERE listId = :listId AND showId = :showId")
    fun deleteShowFromList(listId: Int, showId: Int)
}