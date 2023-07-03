package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.domain.repository.StaffRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface GetVoiceActorsListUseCase {
    suspend operator fun invoke(showId: Int, page: Int): Pair<List<ShowVoiceActorModel>, Boolean>?
}

class GetVoiceActorsListUseCaseImpl @Inject constructor(
    private val staffRepository: StaffRepository
) : GetVoiceActorsListUseCase {
    override suspend fun invoke(showId: Int, page: Int): Pair<List<ShowVoiceActorModel>, Boolean>? =
        coroutineScope {
            staffRepository.getShowVoiceActors(showId, page)
        }
}