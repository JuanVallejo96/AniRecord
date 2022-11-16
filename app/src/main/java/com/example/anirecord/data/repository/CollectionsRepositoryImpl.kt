package com.example.anirecord.data.repository

import com.example.anirecord.data.database.GenreDao
import com.example.anirecord.data.database.TagDao
import com.example.anirecord.domain.repository.CollectionsRepository

class CollectionsRepositoryImpl(private val genreDao: GenreDao, private val tagDao: TagDao) :
    CollectionsRepository {
    // TODO
}