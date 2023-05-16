package com.example.anirecord.di

import com.example.anirecord.domain.repository.CollectionsRepository
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.domain.repository.impl.CollectionsRepositoryImpl
import com.example.anirecord.domain.repository.impl.ShowRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindShowRepository(
        showRepositoryImpl: ShowRepositoryImpl
    ): ShowRepository

    @Binds
    abstract fun bindCollectionsRepositoryImpl(
        collectionsRepositoryImpl: CollectionsRepositoryImpl
    ): CollectionsRepository
}