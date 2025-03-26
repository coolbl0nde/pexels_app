package com.example.feature.presentation.home

import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.feature.presentation.home.domain.usecase.GetFeaturedCollectionsUseCase
import com.example.feature.presentation.home.domain.usecase.GetSearchedPhotosUseCase
import com.example.feature.presentation.home.presentation.HomeViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase
    private lateinit var getSearchedPhotosUseCase: GetSearchedPhotosUseCase

    private lateinit var viewModel: HomeViewModel

    private val fakeFeaturedCollection = FeaturedCollection(
        id = "1",
        title = "Featured Collection",
        timestamp = 1234
    )
    private val fakePagingDataCollections = PagingData.from(listOf(fakeFeaturedCollection))

    private val fakePhoto = Photo(
        id = 1,
        width = 1920,
        height = 1080,
        url = "https://example.com/photo.jpg",
        photographer = "Test Photographer",
        liked = false,
        original = "https://example.com/original_photo.jpg"
    )
    private val fakePagingDataPhotos = PagingData.from(listOf(fakePhoto))

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        getFeaturedCollectionsUseCase = mockk()
        getSearchedPhotosUseCase = mockk()

        coEvery { getFeaturedCollectionsUseCase(perPage = any()) } returns flowOf(fakePagingDataCollections)
        coEvery { getSearchedPhotosUseCase(query = any(), perPage = any()) } returns flowOf(fakePagingDataPhotos)

        viewModel = HomeViewModel(getFeaturedCollectionsUseCase, getSearchedPhotosUseCase)

    }

    @Test
    fun testUpdatePhotos() = runTest {
        testScope.launch {
            viewModel.updatePhotos()

            val itemsSnapshot: List<Photo> = viewModel.searchedPhotos.asSnapshot()

            assertEquals(listOf(fakePhoto), itemsSnapshot)
        }
    }

    @Test
    fun testUpdateFeaturedCollections() = runTest {
        testScope.launch {
            val itemsSnapshot: List<FeaturedCollection> = viewModel.collections.asSnapshot()

            assertEquals(listOf(fakeFeaturedCollection), itemsSnapshot)
        }
    }

    @Test
    fun testResetSearchValue() = runTest {
        testScope.launch {
            viewModel.resetSearchValue(
                listOf(fakeFeaturedCollection) as ItemSnapshotList<FeaturedCollection>
            )

            val firstItem: String = viewModel.searchValue.value

            assertEquals(fakeFeaturedCollection.title, firstItem)
        }
    }

    @Test
    fun testSetSearchValue() {
        viewModel.setSearchValue("new query")
        assertEquals("new query", viewModel.searchValue.value)
    }

    @Test
    fun testFetchData() {
        testScope.launch {
            viewModel.retryFetchData()

            val featuredCollectionsSnapshot: List<FeaturedCollection> = viewModel.collections.asSnapshot()
            val photosSnapshot: List<Photo> = viewModel.searchedPhotos.asSnapshot()

            assertEquals(listOf(fakeFeaturedCollection), featuredCollectionsSnapshot)
            assertEquals(listOf(fakePhoto), photosSnapshot)
        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        clearAllMocks()
    }

}