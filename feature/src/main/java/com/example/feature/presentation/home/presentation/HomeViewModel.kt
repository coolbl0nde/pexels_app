package com.example.feature.presentation.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.model.FeaturedCollection
import com.example.core.model.Photo
import com.example.core.utils.AMOUNT_OF_PAGE_FEATURED_COLLECTIONS
import com.example.core.utils.AMOUNT_OF_PER_PAGE_FEATURED_COLLECTIONS
import com.example.core.utils.AMOUNT_OF_PER_PAGE_PHOTOS
import com.example.core.utils.empty
import com.example.feature.presentation.home.domain.usecase.GetFeaturedCollectionsUseCase
import com.example.feature.presentation.home.domain.usecase.GetSearchedPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase,
    private val getSearchedPhotosUseCase: GetSearchedPhotosUseCase,
): ViewModel() {

    private val _collections = MutableStateFlow<List<FeaturedCollection>>(emptyList())
    val collections: StateFlow<List<FeaturedCollection>> = _collections.asStateFlow()

    private val _selectedItem = MutableStateFlow<String>(String.empty)
    val selectedItem: StateFlow<String> = _selectedItem.asStateFlow()

    private val _searchedPhotos = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val searchedPhotos = _searchedPhotos.asStateFlow()

    init {
        initializeHomeScreen()
    }

    fun updatePhotos() {
        viewModelScope.launch {
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

    private fun initializeHomeScreen(){
        viewModelScope.launch {
            updateFeaturedCollections()

            initSelectedItem(_collections.value)

            updatePhotos()
        }

    }

    private suspend fun updateFeaturedCollections(){
        val result = getFeaturedCollectionsUseCase(
            page = AMOUNT_OF_PAGE_FEATURED_COLLECTIONS,
            perPage = AMOUNT_OF_PER_PAGE_FEATURED_COLLECTIONS,
        )
        _collections.value = result
    }

    private fun initSelectedItem(result: List<FeaturedCollection>){
        if (_selectedItem.value.isEmpty() && result.isNotEmpty()){
            _selectedItem.value = result.first().title
        }
    }

    fun selectItem(title: String) {
        _selectedItem.value = title
    }

}