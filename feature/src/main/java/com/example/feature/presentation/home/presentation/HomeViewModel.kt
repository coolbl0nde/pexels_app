package com.example.feature.presentation.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.core.utils.AMOUNT_OF_PER_PAGE_FEATURED_COLLECTIONS
import com.example.core.utils.AMOUNT_OF_PER_PAGE_PHOTOS
import com.example.core.utils.empty
import com.example.feature.presentation.home.domain.usecase.GetFeaturedCollectionsUseCase
import com.example.feature.presentation.home.domain.usecase.GetSearchedPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase,
    private val getSearchedPhotosUseCase: GetSearchedPhotosUseCase,
) : ViewModel() {

    private val _collections = MutableStateFlow<PagingData<FeaturedCollection>>(PagingData.empty())
    val collections: StateFlow<PagingData<FeaturedCollection>> = _collections.asStateFlow()

    private val _searchValue = MutableStateFlow(String.empty)
    val searchValue: StateFlow<String> = _searchValue.asStateFlow()

    private val _searchedPhotos = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val searchedPhotos = _searchedPhotos.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadInitialData()
    }

    fun loadInitialData(){
        updateFeaturedCollections()
        updatePhotos()
    }

    fun setIsLoading(value: Boolean){
        _isLoading.value = value
    }

    fun updatePhotos() {
        viewModelScope.launch {
            getSearchedPhotosUseCase(
                query = _searchValue.value,
                perPage = AMOUNT_OF_PER_PAGE_PHOTOS
            )
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _searchedPhotos.value = pagingData
                }
        }
    }

    private fun updateFeaturedCollections() {
        viewModelScope.launch {
            getFeaturedCollectionsUseCase(
                perPage = AMOUNT_OF_PER_PAGE_FEATURED_COLLECTIONS
            )
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _collections.value = pagingData
                }
        }
    }

    fun setSearchValue(text: String) {
        _searchValue.value = text
    }

    fun resetSearchValue(featuredCollections: ItemSnapshotList<FeaturedCollection>) {
        if (featuredCollections.isNotEmpty()) {
            val firstItem = featuredCollections.first()?.title

            if (firstItem !== null) {
                setSearchValue(firstItem)
            }
        }
    }

    fun retryFetchData() {
        updateFeaturedCollections()
        updatePhotos()
    }
}
