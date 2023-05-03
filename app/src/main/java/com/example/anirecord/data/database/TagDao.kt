package com.example.anirecord.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anirecord.Constants
import com.example.anirecord.data.entity.MediaTag

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mediaTag: MediaTag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg mediaTags: MediaTag)

    @Query("SELECT * FROM ${Constants.COLLECTIONS_DB_TAG_TABLE_NAME}")
    fun getAll(): LiveData<List<MediaTag>>
}