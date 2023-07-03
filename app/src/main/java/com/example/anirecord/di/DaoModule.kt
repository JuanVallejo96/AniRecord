package com.example.anirecord.di

import com.example.anirecord.data.database.AppDatabase
import com.example.anirecord.data.database.ListDao
import com.example.anirecord.data.database.ShowDao
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
    fun provideShowDao(
        database: AppDatabase
    ): ShowDao = database.getShowDao()

    @Singleton
    @Provides
    fun provideListDao(
        database: AppDatabase
    ): ListDao = database.getListDao()
}