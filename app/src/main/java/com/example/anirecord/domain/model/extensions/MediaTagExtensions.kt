package com.example.anirecord.domain.model.extensions

import com.example.anirecord.data.entity.MediaTag
import com.example.anirecord.domain.model.MediaTagModel
import com.example.anirecord.graphql.TagsQuery

fun TagsQuery.MediaTagCollection.toEntity() = MediaTag(
    id = id,
    name = name,
    description = description,
    category = category,
)

fun MediaTag.toModel() = MediaTagModel(
    name = name,
    description = description,
    category = category,
)