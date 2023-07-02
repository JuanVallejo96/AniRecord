package com.example.anirecord.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.anirecord.Constants

@Entity(tableName = Constants.DB_LIST_TABLE_NAME)
data class ListEntity(
    @PrimaryKey(autoGenerate = true) val listId: Int,
    var name: String,
)

@Entity(
    tableName = Constants.DB_LIST_SHOW_XREFF_TABLE_NAME,
    primaryKeys = ["listId", "showId"],
    indices = [
        Index(value = ["listId", "showId"]),
        Index(value = ["showId", "listId"]),
    ],
    foreignKeys = [
        ForeignKey(
            parentColumns = ["listId"],
            childColumns = ["listId"],
            entity = ListEntity::class,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            parentColumns = ["showId"],
            childColumns = ["showId"],
            entity = ShowEntity::class,
            onDelete = ForeignKey.RESTRICT
        ),
    ]
)
data class ListShowCrossRef(
    val listId: Int,
    val showId: Int,
)

data class ListWithShows(
    @Embedded val list: ListEntity,
    @Relation(
        parentColumn = "listId",
        entityColumn = "showId",
        associateBy = Junction(ListShowCrossRef::class)
    )
    val shows: List<ShowEntity>
)