package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.StaffDetailModel
import com.example.anirecord.domain.repository.StaffRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface GetStaffDetailUseCase {
    suspend operator fun invoke(staffId: Int): StaffDetailModel?
}

class GetStaffDetailUseCaseImpl @Inject constructor(
    private val staffRepository: StaffRepository,
) : GetStaffDetailUseCase {
    override suspend fun invoke(staffId: Int): StaffDetailModel? = coroutineScope {
        staffRepository.getStaffDetails(staffId)
    }
}