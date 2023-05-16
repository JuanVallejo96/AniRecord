package com.example.anirecord.di

import android.content.Context
import androidx.room.Room
import com.example.anirecord.Constants
import com.example.anirecord.data.database.CollectionsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CollectionsDatabase {
        return Room.databaseBuilder(
            context,
            CollectionsDatabase::class.java,
            Constants.COLLECTIONS_DB_NAME
        ).addMigrations(*CollectionsDatabase.migrations.toTypedArray()).build()
    }
}