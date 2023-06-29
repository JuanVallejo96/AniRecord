package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.repository.StaffRepository
import javax.inject.Inject

interface GetStaffListUseCase {
    suspend operator fun invoke(
        showId: Int,
        page: Int
    ): Pair<List<ShowStaffListItemModel>, Boolean>?
}

class GetStaffListUseCaseImpl @Inject constructor(
    private val staffRepository: StaffRepository
) : GetStaffListUseCase {
    override suspend fun invoke(
        showId: Int,
        page: Int
    ): Pair<List<ShowStaffListItemModel>, Boolean>? {
        return staffRepository.getShowStaff(showId, page)
    }
}