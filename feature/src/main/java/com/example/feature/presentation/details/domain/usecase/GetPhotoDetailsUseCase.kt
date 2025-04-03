package com.example.feature.presentation.details.domain.usecase

import com.example.core.model.Photo
import com.example.data_api.repository.PexelsRepository
import javax.inject.Inject

class GetPhotoDetailsUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository,
){
    suspend operator fun invoke(id: Long): Result<Photo> {
        return pexelsRepository.getPhotoDetails(id)
    }
}