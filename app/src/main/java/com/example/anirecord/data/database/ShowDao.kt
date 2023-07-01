package com.example.anirecord.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.anirecord.Constants
import com.example.anirecord.data.entity.ShowEntity

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg shows: ShowEntity)

    @Query("SELECT * FROM ${Constants.DB_SHOW_TABLE_NAME}")
    fun getAll(): LiveData<List<ShowEntity>>

    @Query("SELECT * FROM ${Constants.DB_SHOW_TABLE_NAME} WHERE showId = :id")
    fun findById(id: Int): LiveData<ShowEntity>

    @Query("SELECT * FROM ${Constants.DB_SHOW_TABLE_NAME} WHERE isFavourite = 1")
    fun getFavourites(): LiveData<List<ShowEntity>>

    @Update
    fun update(show: ShowEntity)

    @Delete
    fun delete(vararg shows: ShowEntity)
}