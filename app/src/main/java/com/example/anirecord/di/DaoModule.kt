package com.example.anirecord.di

import com.example.anirecord.data.database.CollectionsDatabase
import com.example.anirecord.data.database.GenreDao
import com.example.anirecord.data.database.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideGenreDao(
        collectionsDatabase: CollectionsDatabase
    ): GenreDao = collectionsDatabase.getGenreDao()

    @Singleton
    @Provides
    fun provideTagDao(
        collectionsDatabase: CollectionsDatabase
    ): TagDao = collectionsDatabase.getTagDao()
}