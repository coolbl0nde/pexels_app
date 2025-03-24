package com.example.feature.presentation.home.domain.usecase

import androidx.paging.PagingData
import com.example.core.model.Photo
import com.example.data_api.entity.PhotoEntity
import com.example.data_api.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchedPhotosUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository
) {
    operator fun invoke(query: String, perPage: Int): Flow<PagingData<Photo>> {
        return pexelsRepository.getSearchedPhotos(query, perPage)
    }
}