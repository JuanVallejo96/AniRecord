package com.example.anirecord.domain.repository

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.model.ShowVoiceActorModel

interface StaffRepository {
    suspend fun getShowVoiceActors(
        showId: Int,
        page: Int
    ): Pair<List<ShowVoiceActorModel>, Boolean>?

    suspend fun getShowStaff(
        showId: Int,
        page: Int
    ): Pair<List<ShowStaffListItemModel>, Boolean>?
}