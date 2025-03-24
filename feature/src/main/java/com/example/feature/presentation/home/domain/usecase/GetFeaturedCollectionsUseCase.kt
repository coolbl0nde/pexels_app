package com.example.feature.presentation.home.domain.usecase

import androidx.paging.PagingData
import com.example.core.model.FeaturedCollection
import com.example.data_api.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeaturedCollectionsUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository
) {
    operator fun invoke(perPage: Int): Flow<PagingData<FeaturedCollection>> {
        return pexelsRepository.getFeaturedCollections(perPage)
    }
}