package com.example.feature.presentation.bookmarks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.model.Photo
import com.example.core.utils.AMOUNT_OF_PER_PAGE_PHOTOS
import com.example.feature.presentation.bookmarks.domain.usecase.GetBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
): ViewModel() {

    private val _bookmarks = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val bookmarks = _bookmarks.asStateFlow()

    init {
        getBookmarks()
    }

    private fun getBookmarks(){
        viewModelScope.launch {
            getBookmarksUseCase(
                perPage = AMOUNT_OF_PER_PAGE_PHOTOS
            )
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _bookmarks.value = pagingData
                }
        }
    }


}