package com.example.anirecord.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anirecord.Constants

@Entity(tableName = Constants.COLLECTIONS_DB_GENRE_TABLE_NAME)
data class Genre(
    @PrimaryKey
    val name: String
)