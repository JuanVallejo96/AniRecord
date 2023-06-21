package com.example.anirecord.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.data.database.GenreDao
import com.example.anirecord.data.database.TagDao
import com.example.anirecord.data.entity.Genre
import com.example.anirecord.data.entity.MediaTag
import com.example.anirecord.domain.model.GenreModel
import com.example.anirecord.domain.model.MediaTagModel
import com.example.anirecord.domain.model.extensions.toEntity
import com.example.anirecord.domain.model.extensions.toModel
import com.example.anirecord.domain.repository.CollectionsRepository
import com.example.anirecord.graphql.GenresQuery
import com.example.anirecord.graphql.TagsQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectionsRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val genreDao: GenreDao,
    private val tagDao: TagDao,
) : CollectionsRepository {
    override suspend fun getTags(): LiveData<List<MediaTagModel>> {
        return tagDao.getAll().map {
            it.map(MediaTag::toModel)
        }
    }

    override suspend fun getGenres(): LiveData<List<GenreModel>> {
        return genreDao.getAll().map {
            it.map(Genre::toModel)
        }
    }

    override suspend fun refreshTags(): Unit = withContext(Dispatchers.IO) {
        apolloClient.query(TagsQuery()).execute().data?.MediaTagCollection
            ?.filterNotNull()
            ?.map(TagsQuery.MediaTagCollection::toEntity)
            ?.let { tags ->
                tagDao.insertAll(*tags.toTypedArray())
            }
    }

    override suspend fun refreshGenres(): Unit = withContext(Dispatchers.IO) {
        apolloClient.query(GenresQuery()).execute().data?.GenreCollection
            ?.filterNotNull()
            ?.map { Genre(it) }
            ?.let { genres ->
                genreDao.insertAll(*genres.toTypedArray())
            }
    }
}