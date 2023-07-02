package com.example.anirecord.di

import com.example.anirecord.domain.usecase.AddToListUseCase
import com.example.anirecord.domain.usecase.AddToListUseCaseImpl
import com.example.anirecord.domain.usecase.CreateListUseCase
import com.example.anirecord.domain.usecase.CreateListUseCaseImpl
import com.example.anirecord.domain.usecase.DeleteListUseCase
import com.example.anirecord.domain.usecase.DeleteListUseCaseImpl
import com.example.anirecord.domain.usecase.DeleteShowFromListUseCase
import com.example.anirecord.domain.usecase.DeleteShowFromListUseCaseImpl
import com.example.anirecord.domain.usecase.GetAllListsUseCase
import com.example.anirecord.domain.usecase.GetAllListsUseCaseImpl
import com.example.anirecord.domain.usecase.GetFavouritesUseCase
import com.example.anirecord.domain.usecase.GetFavouritesUseCaseImpl
import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.domain.usecase.GetPopularUseCaseImpl
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.GetShowDetailUseCaseImpl
import com.example.anirecord.domain.usecase.GetShowsOnListUseCase
import com.example.anirecord.domain.usecase.GetShowsOnListUseCaseImpl
import com.example.anirecord.domain.usecase.GetStaffListUseCase
import com.example.anirecord.domain.usecase.GetStaffListUseCaseImpl
import com.example.anirecord.domain.usecase.GetVoiceActorsListUseCase
import com.example.anirecord.domain.usecase.GetVoiceActorsListUseCaseImpl
import com.example.anirecord.domain.usecase.SearchByNameUseCase
import com.example.anirecord.domain.usecase.SearchByNameUseCaseImpl
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCase
import com.example.anirecord.domain.usecase.ToggleFavouriteUseCaseImpl
import com.example.anirecord.domain.usecase.UpdateListUseCase
import com.example.anirecord.domain.usecase.UpdateListUseCaseImpl
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

    @Binds
    abstract fun bindGetAllListsUseCase(
        getAllListsUseCase: GetAllListsUseCaseImpl
    ): GetAllListsUseCase

    @Binds
    abstract fun bindGetShowsOnListUseCase(
        getShowsOnListUseCase: GetShowsOnListUseCaseImpl
    ): GetShowsOnListUseCase

    @Binds
    abstract fun bindCreateListUseCase(
        createListUseCase: CreateListUseCaseImpl
    ): CreateListUseCase

    @Binds
    abstract fun bindAddToListUseCase(
        AddToListUseCase: AddToListUseCaseImpl
    ): AddToListUseCase

    @Binds
    abstract fun bindUpdateListUseCase(
        updateListUseCase: UpdateListUseCaseImpl
    ): UpdateListUseCase

    @Binds
    abstract fun bindDeleteListUseCase(
        deleteListUseCase: DeleteListUseCaseImpl
    ): DeleteListUseCase

    @Binds
    abstract fun bindDeleteShowFromListUseCase(
        deleteShowFromListUseCase: DeleteShowFromListUseCaseImpl
    ): DeleteShowFromListUseCase
}