package com.example.api_impl.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.api_impl.mapper.SearchPhotosResponseMapper
import com.example.core.model.Photo
import com.example.data_api.api.PexelsApi

class PhotosPagingSource (
    private val pexelsApi: PexelsApi,
    private val query: String,
    private val searchPhotosResponseMapper: SearchPhotosResponseMapper,
): PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val searchedPhotosResponse = pexelsApi.getSearchedPhotos(
                query = query,
                page = page,
                perPage = 30
            )

            val searchedPhotos = searchPhotosResponseMapper.map(searchedPhotosResponse)

            LoadResult.Page(
                data = searchedPhotos.photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (searchedPhotos.photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}