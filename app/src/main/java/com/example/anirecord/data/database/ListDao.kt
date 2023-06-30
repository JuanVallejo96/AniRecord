package com.example.anirecord.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.anirecord.Constants
import com.example.anirecord.data.entity.ListEntity
import com.example.anirecord.data.entity.ListWithItemCount
import com.example.anirecord.data.entity.ListWithShows

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg list: ListEntity)

    @Query("SELECT * FROM ${Constants.DB_LIST_TABLE_NAME} WHERE listId = :id")
    fun findById(id: Int): LiveData<ListEntity>

    @Query("SELECT * FROM ${Constants.DB_LIST_TABLE_NAME} WHERE listId = :id")
    fun findWithShowsById(id: Int): LiveData<ListWithShows>

    @Query("SELECT *, COUNT(*) as itemCount FROM ${Constants.DB_LIST_TABLE_NAME} l JOIN ${Constants.DB_LIST_SHOW_XREFF_TABLE_NAME} xr ON (l.listId = xr.listId) GROUP BY l.listId")
    fun findAll(): LiveData<List<ListWithItemCount>>

    @Update
    fun update(list: ListEntity)

    @Delete
    fun delete(vararg lists: ListEntity)
}