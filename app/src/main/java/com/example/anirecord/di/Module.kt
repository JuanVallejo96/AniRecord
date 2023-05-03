package com.example.anirecord.di

import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.example.anirecord.Constants
import com.example.anirecord.data.database.CollectionsDatabase
import com.example.anirecord.domain.repository.CollectionsRepository
import com.example.anirecord.domain.repository.CollectionsRepositoryImpl
import com.example.anirecord.domain.repository.ShowRepository
import com.example.anirecord.domain.repository.ShowRepositoryImpl
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.domain.usecase.GetPopularUseCaseImpl
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.GetShowDetailUseCaseImpl
import com.example.anirecord.domain.usecase.GetTagsUseCase
import com.example.anirecord.domain.usecase.GetTagsUseCaseImpl
import com.example.anirecord.ui.home.HomeViewModel
import com.example.anirecord.ui.showdetail.ShowDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
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
    single<CollectionsRepository> { CollectionsRepositoryImpl(get(), get(), get()) }
    single<ShowRepository> { ShowRepositoryImpl(get()) }
}

private val useCaseModule = module {
    single<GetPopularUseCase> { GetPopularUseCaseImpl(get()) }
    single<GetTagsUseCase> { GetTagsUseCaseImpl(get()) }
    single<GetShowDetailUseCase> { GetShowDetailUseCaseImpl(get()) }
}

private val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModelOf(::ShowDetailViewModel)
}