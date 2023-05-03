package com.example.anirecord.domain.usecase

import androidx.lifecycle.LiveData
import com.example.anirecord.domain.model.MediaTagModel
import com.example.anirecord.domain.repository.CollectionsRepository

interface GetTagsUseCase {
    suspend operator fun invoke(): LiveData<List<MediaTagModel>>
}

class GetTagsUseCaseImpl(private val collectionsRepository: CollectionsRepository) :
    GetTagsUseCase {
    override suspend fun invoke(): LiveData<List<MediaTagModel>> = collectionsRepository.getTags()
}