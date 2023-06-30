package com.example.anirecord.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.anirecord.data.entity.ListEntity
import com.example.anirecord.data.entity.ShowEntity

@Database(entities = [ShowEntity::class, ListEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getShowDao(): ShowDao
    abstract fun getListDao(): ListDao

    companion object {
        val migrations: List<Migration> = listOf()
        private var instance: AppDatabase? = null
    }
}