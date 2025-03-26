package com.example.feature.domain.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.data_api.repository.PexelsRepository
import com.example.feature.presentation.home.domain.usecase.GetFeaturedCollectionsUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetFeaturedCollectionsUseCaseTest {

    private lateinit var repository: PexelsRepository
    private lateinit var useCase: GetFeaturedCollectionsUseCase

    @Before
    fun setup(){
        repository = mockk()
        useCase = GetFeaturedCollectionsUseCase(repository)
    }

    @Test
    fun testGetFeaturedCollections() = runTest {
        val perPage = 10

        val fakeFeaturedCollection = FeaturedCollection(
            id = "1",
            title = "cats",
            timestamp = 1234,
        )

        val fakePagingData = PagingData.from(listOf(fakeFeaturedCollection))

        coEvery { repository.getFeaturedCollections(perPage) } returns flowOf(fakePagingData)

        val resultFlow = repository.getFeaturedCollections(perPage).asSnapshot()

        assertEquals(listOf(fakeFeaturedCollection), resultFlow)
    }

    @After
    fun tearDown(){
        clearAllMocks()
    }
}