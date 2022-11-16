package com.example.anirecord.di

import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.Constants
import com.example.anirecord.data.database.CollectionsDatabase
import com.example.anirecord.data.repository.CollectionsRepositoryImpl
import com.example.anirecord.domain.repository.CollectionsRepository
import com.example.anirecord.domain.repository.SeriesRepository
import com.example.anirecord.domain.repository.SeriesRepositoryImpl
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.domain.usecase.GetPopularUseCaseImpl
import com.example.anirecord.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectModules() = loadModules

private val loadModules by lazy {
    loadKoinModules(
        listOf(
            networkModule,
            databaseModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
        )
    )
}

private val networkModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl(Constants.API_URL)
            .build()
    }
}

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CollectionsDatabase::class.java,
            Constants.COLLECTIONS_DB_NAME
        ).addMigrations(*CollectionsDatabase.migrations.toTypedArray()).build()
    }
    single { get<CollectionsDatabase>().getGenreDao() }
    single { get<CollectionsDatabase>().getTagDao() }
}

private val repositoryModule = module {
    single<CollectionsRepository> { CollectionsRepositoryImpl(get(), get()) }
    single<SeriesRepository> { SeriesRepositoryImpl(get()) }
}

private val viewModelModule = module {
    viewModel { HomeViewModel() }
}

private val useCaseModule = module {
    single<GetPopularUseCase> { GetPopularUseCaseImpl(get()) }
}