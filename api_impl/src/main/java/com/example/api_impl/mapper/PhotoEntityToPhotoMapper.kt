package com.example.api_impl.mapper

import com.example.core.model.Photo
import com.example.data_api.entity.PhotoEntity
import javax.inject.Inject

class PhotoEntityToPhotoMapper @Inject constructor(){

    fun map(photoEntity: PhotoEntity): Photo {
        return Photo(
            id = photoEntity.id,
            width = photoEntity.width,
            height = photoEntity.height,
            url = photoEntity.url,
            photographer = photoEntity.photographer,
            liked = photoEntity.liked,
            original = photoEntity.original,
            isFavorite = photoEntity.isFavorite,
        )
    }
}