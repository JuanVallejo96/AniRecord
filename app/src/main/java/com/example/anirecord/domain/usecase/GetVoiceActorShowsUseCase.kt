package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface GetVoiceActorShowsUseCase {
    suspend operator fun invoke(
        id: Int,
        page: Int
    ): Pair<List<VoiceActorShowsListItemModel>, Boolean>?
}

class GetVoiceActorShowsUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository,
) : GetVoiceActorShowsUseCase {
    override suspend fun invoke(
        id: Int,
        page: Int
    ): Pair<List<VoiceActorShowsListItemModel>, Boolean>? = coroutineScope {
        showRepository.getVoiceActorShows(id, page)
    }
}