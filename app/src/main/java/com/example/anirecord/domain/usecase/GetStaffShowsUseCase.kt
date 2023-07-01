package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface GetStaffShowsUseCase {
    suspend operator fun invoke(
        staffId: Int,
        page: Int,
    ): Pair<List<StaffShowListItemModel>, Boolean>?
}

class GetStaffShowsUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) : GetStaffShowsUseCase {
    override suspend fun invoke(
        staffId: Int,
        page: Int,
    ): Pair<List<StaffShowListItemModel>, Boolean>? = showRepository.getStaffShows(staffId, page)
}