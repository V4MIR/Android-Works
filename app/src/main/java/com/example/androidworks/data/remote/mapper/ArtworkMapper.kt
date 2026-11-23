package com.example.androidworks.data.remote.mapper

import com.example.androidworks.data.remote.dto.ArtworkDetailsDto
import com.example.androidworks.domain.model.ArtworkDetails

fun ArtworkDetailsDto.toDomain(): ArtworkDetails = ArtworkDetails(
    objectId = objectId,
    title = title.orEmpty(),
    artistDisplayName = artistDisplayName.orEmpty(),
    artistDisplayBio = artistDisplayBio.orEmpty(),
    objectDate = objectDate.orEmpty(),
    medium = medium.orEmpty(),
    dimensions = dimensions.orEmpty(),
    classification = classification.orEmpty(),
    department = department.orEmpty(),
    culture = culture.orEmpty(),
    period = period.orEmpty(),
    dynasty = dynasty.orEmpty(),
    country = country.orEmpty(),
    city = city.orEmpty(),
    creditLine = creditLine.orEmpty(),
    primaryImage = primaryImage.orEmpty(),
    primaryImageSmall = primaryImageSmall.orEmpty(),
    isPublicDomain = isPublicDomain ?: false,
    isHighlight = isHighlight ?: false,
    galleryNumber = galleryNumber.orEmpty(),
    objectURL = objectURL.orEmpty(),
    tags = tags?.mapNotNull { it.term } ?: emptyList()
)