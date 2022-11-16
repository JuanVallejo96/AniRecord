package com.example.anirecord.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.anirecord.Constants
import com.example.anirecord.domain.model.GenreModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.COLLECTIONS_DB_GENRE_TABLE_NAME)
data class Genre(
    @PrimaryKey
    override val name: String
) : Parcelable, GenreModel