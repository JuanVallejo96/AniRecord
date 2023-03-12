package com.example.anirecord.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anirecord.Constants
import com.example.anirecord.data.model.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg genres: Genre)

    @Query("SELECT * FROM ${Constants.COLLECTIONS_DB_GENRE_TABLE_NAME}")
    fun getAll(): LiveData<List<Genre>>
}