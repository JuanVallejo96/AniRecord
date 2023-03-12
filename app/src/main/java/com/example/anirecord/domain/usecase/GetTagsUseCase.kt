package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.TagModel
import com.example.anirecord.domain.repository.CollectionsRepository

interface GetTagsUseCase {
    suspend operator fun invoke(): LiveData<List<TagModel>>
}

class GetTagsUseCaseImpl(private val collectionsRepository: CollectionsRepository) :
    GetTagsUseCase {
    override suspend fun invoke(): LiveData<List<TagModel>> = collectionsRepository.getTags()
}