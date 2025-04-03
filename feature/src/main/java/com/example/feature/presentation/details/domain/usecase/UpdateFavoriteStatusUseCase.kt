package com.example.feature.presentation.details.domain.usecase

import com.example.data_api.repository.PexelsRepository
import javax.inject.Inject

class UpdateFavoriteStatusUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository,
) {
    suspend operator fun invoke(id: Long, isFavorite: Boolean) {
        return pexelsRepository.updateFavoriteStatus(
            photoId = id,
            isFavorite = isFavorite,
        )
    }
}