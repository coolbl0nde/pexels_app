package com.example.feature.presentation.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core.model.Photo
import com.example.core.utils.AMOUNT_OF_PER_PAGE_FEATURED_COLLECTIONS
import com.example.core.utils.AMOUNT_OF_PER_PAGE_PHOTOS
import com.example.core.utils.FIRST_SELECTED_INDEX
import com.example.core.utils.empty
import com.example.feature.presentation.home.domain.usecase.GetFeaturedCollectionsUseCase
import com.example.feature.presentation.home.domain.usecase.GetSearchedPhotosUseCase
import com.example.feature.presentation.home.presentation.mapper.FeaturedCollectionDomainToUiMapper
import com.example.feature.presentation.home.presentation.model.FeaturedCollectionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase,
    private val getSearchedPhotosUseCase: GetSearchedPhotosUseCase,
    private val featuredCollectionDomainToUiMapper: FeaturedCollectionDomainToUiMapper,
) : ViewModel() {

    private val _collections = MutableStateFlow<PagingData<FeaturedCollectionUi>>(PagingData.empty())
    val collections: StateFlow<PagingData<FeaturedCollectionUi>> = _collections.asStateFlow()

    private val _selectedItem = MutableStateFlow(String.empty)
    val selectedItem: StateFlow<String> = _selectedItem.asStateFlow()

    private val _searchedPhotos = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val searchedPhotos = _searchedPhotos.asStateFlow()

    init {
        updateFeaturedCollections()
        updateSelectedItem(FIRST_SELECTED_INDEX)
        updatePhotos()
    }

    fun updatePhotos() {
        viewModelScope.launch {
            Log.d("tag", "${_selectedItem.value}")

            getSearchedPhotosUseCase(
                query = _selectedItem.value,
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
                    _collections.value = pagingData.map {
                        featuredCollectionDomainToUiMapper.map(it)
                    }
                }
        }
    }

    fun updateSelectedItem(selectedIndex: Int) {
        viewModelScope.launch {
            val tabs = _collections.value.map { collection ->
                val isSelected = selectedIndex == collection.index
                if (isSelected) {
                    _selectedItem.value = collection.title
                }

                collection.copy(isSelected = isSelected)
            }

            _collections.value = tabs
        }
    }

    fun selectItem(title: String) {
        _selectedItem.value = title
    }

    fun onSearchRequest(text: String) {
        _selectedItem.value = text
    }

    fun retryFetchData() {
        updateFeaturedCollections()
    }
}
