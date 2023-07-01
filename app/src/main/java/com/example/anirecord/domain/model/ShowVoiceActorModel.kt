package com.example.anirecord.domain.model

data class ShowVoiceActorModel(
    val actorId: Int,
    val actorName: String,
    val actorImage: String,
    val characterName: String,
    val characterImage: String,
    val roleDetails: String?,
)