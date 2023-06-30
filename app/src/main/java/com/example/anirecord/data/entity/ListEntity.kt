package com.example.anirecord.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.anirecord.Constants

@Entity(tableName = Constants.DB_LIST_TABLE_NAME)
data class ListEntity(
    @PrimaryKey val listId: Int,
    val name: String,
)

@Entity(tableName = Constants.DB_LIST_SHOW_XREFF_TABLE_NAME, primaryKeys = ["listId", "showId"])
data class ListShowCrossRef(
    val listId: Int,
    val showId: Int,
)

data class ListWithItemCount(
    @Embedded val list: ListEntity,
    val itemCount: Int,
)

data class ListWithShows(
    @Embedded val list: ListEntity,
    @Relation(
        parentColumn = "listId",
        entity = ShowEntity::class,
        entityColumn = "showId",
        associateBy = Junction(
            value = ListShowCrossRef::class,
            parentColumn = "lId",
            entityColumn = "showId",
        )
    )
    val shows: List<ShowEntity>
)