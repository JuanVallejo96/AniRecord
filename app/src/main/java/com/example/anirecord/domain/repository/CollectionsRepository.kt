package com.example.anirecord.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.TagsQuery
import com.example.anirecord.data.database.GenreDao
import com.example.anirecord.data.database.TagDao
import com.example.anirecord.domain.model.TagModel
import com.example.anirecord.domain.model.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CollectionsRepository {
    suspend fun getTags(): LiveData<List<TagModel>>

    suspend fun refreshTags()
}

class CollectionsRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val genreDao: GenreDao,
    private val tagDao: TagDao
) : CollectionsRepository {
    override suspend fun getTags(): LiveData<List<TagModel>> {
        // Kotlin says fuck polymorphism if not mapped
        return tagDao.getAll().map { it }
    }

    override suspend fun refreshTags(): Unit = withContext(Dispatchers.IO) {
        apolloClient.query(TagsQuery()).execute().data?.MediaTagCollection
            ?.filterNotNull()
            ?.map(TagsQuery.MediaTagCollection::asDbModel)
            ?.let { tags ->
                tagDao.insertAll(*tags.toTypedArray())
            }
    }
}