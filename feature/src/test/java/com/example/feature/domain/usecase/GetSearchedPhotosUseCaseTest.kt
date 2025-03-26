package com.example.feature.domain.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.core.model.Photo
import com.example.data_api.repository.PexelsRepository
import com.example.feature.presentation.home.domain.usecase.GetSearchedPhotosUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetSearchedPhotosUseCaseTest {

    private lateinit var useCase: GetSearchedPhotosUseCase
    private lateinit var repository: PexelsRepository

    @Before
    fun setup(){
        repository = mockk()
        useCase = GetSearchedPhotosUseCase(repository)
    }

    @Test
    fun testGetSearchedPhotos() = runTest {
        val query = "cats"
        val perPage = 10

        val fakePhoto = Photo(
            id = 1,
            width = 1920,
            height = 1080,
            url = "https://example.com/photo.jpg",
            photographer = "Test Photographer",
            liked = false,
            original = "https://example.com/original_photo.jpg"
        )

        val fakePagingData = PagingData.from(listOf(fakePhoto))

        coEvery { repository.getSearchedPhotos(query, perPage) } returns flowOf(fakePagingData)

        val resultFlow = repository.getSearchedPhotos(query, perPage).asSnapshot()

        assertEquals(listOf(fakePhoto), resultFlow)
    }

    @After
    fun tearDown(){
        clearAllMocks()
    }

}