package com.example.anirecord.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anirecord.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.COLLECTIONS_DB_TAG_TABLE_NAME)
data class MediaTag(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val category: String?,
) : Parcelable