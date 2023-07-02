package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.repository.StaffRepository
import kotlinx.coroutines.coroutineScope
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
    ): Pair<List<ShowStaffListItemModel>, Boolean>? = coroutineScope {
        staffRepository.getShowStaff(showId, page)
    }
}