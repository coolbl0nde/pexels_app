package com.example.feature.presentation.details.domain.usecase

import com.example.data_api.model.PhotoResult
import com.example.data_api.repository.PexelsRepository
import javax.inject.Inject

class GetPhotoDetailsByIdUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository,
){
    suspend operator fun invoke(id: Long): PhotoResult {
        return pexelsRepository.getPhotoByIdFromNetwork(id = id)
    }
}