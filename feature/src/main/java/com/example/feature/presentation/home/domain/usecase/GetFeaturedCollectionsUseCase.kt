package com.example.feature.presentation.home.domain.usecase

import com.example.core.model.FeaturedCollection
import com.example.data_api.repository.PexelsRepository
import javax.inject.Inject

class GetFeaturedCollectionsUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository
) {
    suspend operator fun invoke(page: Int, perPage: Int): List<FeaturedCollection>{
        return pexelsRepository.getFeaturedCollections(page, perPage)
    }
}