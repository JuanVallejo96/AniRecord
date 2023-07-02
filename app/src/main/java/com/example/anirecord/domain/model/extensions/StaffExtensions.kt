package com.example.anirecord.domain.model.extensions

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.domain.model.StaffDetailModel
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.ShowStaffQuery
import com.example.anirecord.graphql.StaffDetailsQuery

fun StaffDetailsQuery.Staff.toModel(): StaffDetailModel {
    return StaffDetailModel(
        id = id,
        name = name!!.full!!,
        cover = image!!.large!!,
        description = description,
    )
}

fun ShowDetailQuery.Staff.toModelList(): List<ShowStaffListItemModel> {
    return edges?.filterNotNull()?.map {
        ShowStaffListItemModel(
            id = it.node!!.id,
            name = it.node.name!!.full!!,
            image = it.node.image!!.large!!,
            role = it.role!!,
        )
    } ?: listOf()
}

fun ShowStaffQuery.Staff.toModelList(): List<ShowStaffListItemModel> {
    return edges?.filterNotNull()?.map {
        ShowStaffListItemModel(
            id = it.node!!.id,
            name = it.node.name!!.full!!,
            image = it.node.image!!.large!!,
            role = it.role!!,
        )
    } ?: listOf()
}