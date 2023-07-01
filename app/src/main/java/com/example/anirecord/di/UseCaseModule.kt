package com.example.anirecord.di

import com.example.anirecord.domain.usecase.GetFavouritesUseCase
import com.example.anirecord.domain.usecase.GetFavouritesUseCaseImpl
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.domain.usecase.GetPopularUseCaseImpl
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.GetShowDetailUseCaseImpl
import com.example.anirecord.domain.usecase.GetStaffListUseCase
import com.example.anirecord.domain.usecase.GetStaffListUseCaseImpl
import com.example.anirecord.domain.usecase.GetVoiceActorsListUseCase
import com.example.anirecord.domain.usecase.GetVoiceActorsListUseCaseImpl
import com.example.anirecord.domain.usecase.SearchByNameUseCase
import com.example.anirecord.domain.usecase.SearchByNameUseCaseImpl
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCase
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetPopularUseCase(
        getPopularUseCaseImpl: GetPopularUseCaseImpl
    ): GetPopularUseCase

    @Binds
    abstract fun bindGetShowDetailsUseCase(
        getShowDetailUseCaseImpl: GetShowDetailUseCaseImpl
    ): GetShowDetailUseCase

    @Binds
    abstract fun bindGetShowVoiceActorsUseCase(
        getShowVoiceActorsList: GetVoiceActorsListUseCaseImpl
    ): GetVoiceActorsListUseCase

    @Binds
    abstract fun bindGetShowStaffUseCase(
        getShowStaffList: GetStaffListUseCaseImpl
    ): GetStaffListUseCase

    @Binds
    abstract fun bindSearchByNameUseCase(
        searchByNameUseCase: SearchByNameUseCaseImpl
    ): SearchByNameUseCase

    @Binds
    abstract fun bindGetFavouritesUseCase(
        getFavouritesUseCase: GetFavouritesUseCaseImpl
    ): GetFavouritesUseCase

    @Binds
    abstract fun bindToggleFavouriteUseCase(
        toggleFavouriteUseCase: ToggleFavouriteUseCaseImpl
    ): ToggleFavouriteUseCase
}