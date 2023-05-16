package com.example.anirecord.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anirecord.Constants

@Entity(tableName = Constants.COLLECTIONS_DB_TAG_TABLE_NAME)
data class MediaTag(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val category: String?,
)