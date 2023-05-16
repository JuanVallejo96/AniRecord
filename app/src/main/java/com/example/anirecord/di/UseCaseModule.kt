package com.example.anirecord.di

import com.example.anirecord.domain.usecase.GetPopularUseCase
import com.example.anirecord.domain.usecase.GetPopularUseCaseImpl
import com.example.anirecord.domain.usecase.GetShowDetailUseCase
import com.example.anirecord.domain.usecase.GetShowDetailUseCaseImpl
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
}