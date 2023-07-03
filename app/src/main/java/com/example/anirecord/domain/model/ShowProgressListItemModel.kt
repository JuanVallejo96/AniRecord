package com.example.anirecord.domain.model

data class ShowProgressListItemModel(
    val id: Int,
    val name: String?,
    val cover: String?,
    val progress: Int,
    val totalEpisodes: Int?,
)