package com.example.anirecord.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.anirecord.Constants

@Entity(tableName = Constants.DB_SHOW_TABLE_NAME)
data class ShowEntity(
    @PrimaryKey
    val showId: Int,
    val name: String,
    val cover: String,
    var progress: Int?,
    var isFavourite: Boolean,
)

data class ShowWithLists(
    @Embedded val show: ShowEntity,
    @Relation(
        parentColumn = "showId",
        entityColumn = "listId",
        associateBy = Junction(ListShowCrossRef::class)
    )
    val lists: List<ListEntity>
)