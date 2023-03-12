package com.example.anirecord.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.anirecord.data.model.Genre
import com.example.anirecord.data.model.MediaTag

@Database(entities = [Genre::class, MediaTag::class], exportSchema = false, version = 1)
abstract class CollectionsDatabase : RoomDatabase() {
    abstract fun getGenreDao(): GenreDao
    abstract fun getTagDao(): TagDao

    companion object {
        val migrations: List<Migration> = listOf()
    }
}