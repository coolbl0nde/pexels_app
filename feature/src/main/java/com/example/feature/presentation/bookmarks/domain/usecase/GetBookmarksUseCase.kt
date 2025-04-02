package com.example.feature.presentation.bookmarks.domain.usecase

import androidx.paging.PagingData
import com.example.core.model.Photo
import com.example.data_api.repository.PexelsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val pexelsRepository: PexelsRepository,
) {
    operator fun invoke(perPage: Int): Flow<PagingData<Photo>>{
        return pexelsRepository.getBookmarks(perPage = perPage)
    }
}