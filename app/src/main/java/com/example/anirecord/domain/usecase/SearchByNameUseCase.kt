package com.example.anirecord.domain.usecase

import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.domain.repository.ShowRepository
import javax.inject.Inject

interface SearchByNameUseCase {
    suspend operator fun invoke(
        query: String,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>?
}

class SearchByNameUseCaseImpl @Inject constructor(
    private val showRepository: ShowRepository
) :
    SearchByNameUseCase {
    override suspend fun invoke(
        query: String,
        page: Int,
    ): Pair<List<ShowListItemModel>, Boolean>? =
        showRepository.searchByQuery(query, page)
}