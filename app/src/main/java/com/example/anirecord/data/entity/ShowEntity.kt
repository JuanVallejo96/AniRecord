package com.example.anirecord.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anirecord.Constants

@Entity(tableName = Constants.DB_SHOW_TABLE_NAME)
data class ShowEntity(
    @PrimaryKey
    val showId: Int,
    val name: String,
    val cover: String,
    var progress: Int,
    var isFavourite: Boolean,
)