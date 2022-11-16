package com.example.anirecord.domain.model

import com.example.anirecord.SeasonPopularQuery

interface SeriesModel {
    val id: Int
    val name: String?
    val cover: String?
    override fun equals(other: Any?): Boolean
}

fun SeasonPopularQuery.Medium.asModel() = object : SeriesModel {
    private val series = this@asModel

    override val id get() = series.id
    override val name get() = series.title?.english ?: series.title?.romaji
    override val cover get() = series.coverImage?.large
    override fun equals(other: Any?): Boolean = when (other) {
        is SeasonPopularQuery.Medium -> series == other
        is SeriesModel -> id == other.id
        else -> false
    }
}