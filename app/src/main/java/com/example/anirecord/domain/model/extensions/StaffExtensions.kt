package com.example.anirecord.domain.model.extensions

import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.graphql.ShowDetailQuery
import com.example.anirecord.graphql.ShowStaffQuery

fun ShowDetailQuery.Staff.toModelList(): List<ShowStaffListItemModel> {
    return edges?.filterNotNull()?.map {
        ShowStaffListItemModel(
            name = it.node!!.name!!.full!!,
            image = it.node.image!!.large!!,
            role = it.role!!,
        )
    } ?: listOf()
}

fun ShowStaffQuery.Staff.toModelList(): List<ShowStaffListItemModel> {
    return edges?.filterNotNull()?.map {
        ShowStaffListItemModel(
            name = it.node!!.name!!.full!!,
            image = it.node.image!!.large!!,
            role = it.role!!,
        )
    } ?: listOf()
}