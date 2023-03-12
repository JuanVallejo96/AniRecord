package com.example.anirecord.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anirecord.Constants
import com.example.anirecord.domain.model.TagModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.COLLECTIONS_DB_TAG_TABLE_NAME)
data class MediaTag(
    @PrimaryKey
    val id: Int,
    override val name: String,
    override val description: String?,
    override val category: String?,
) : Parcelable, TagModel