package com.example.anirecord.domain.repository

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.GenreModel
import com.example.anirecord.domain.model.MediaTagModel

interface CollectionsRepository {
    suspend fun getTags(): LiveData<List<MediaTagModel>>

    suspend fun getGenres(): LiveData<List<GenreModel>>

    suspend fun refreshTags()

    suspend fun refreshGenres()
}