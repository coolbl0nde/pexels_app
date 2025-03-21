package com.example.feature.presentation.home.presentation.mapper

import com.example.core.model.FeaturedCollection
import com.example.feature.presentation.home.presentation.model.FeaturedCollectionUi
import javax.inject.Inject

class FeaturedCollectionDomainToUiMapper @Inject constructor(){

    fun map(domain: FeaturedCollection): FeaturedCollectionUi {
        return FeaturedCollectionUi(
            id = domain.id,
            title = domain.title,
            timestamp = domain.timestamp,
            index = domain.index,
            isSelected = if (domain.index == 1) true else false
        )
    }

}