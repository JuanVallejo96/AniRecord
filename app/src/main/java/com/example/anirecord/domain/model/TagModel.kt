package com.example.anirecord.domain.model

import com.example.anirecord.TagsQuery
import com.example.anirecord.data.model.MediaTag

interface TagModel {
    val name: String
    val description: String?
    val category: String?
}

fun TagsQuery.MediaTagCollection.asModel() = object : TagModel {
    private val tag = this@asModel

    override val name get() = tag.name
    override val description get() = tag.description
    override val category get() = tag.category
}

fun TagsQuery.MediaTagCollection.asDbModel(): MediaTag {
    return MediaTag(
        id = id,
        name = name,
        description = description,
        category = category
    )
}