package com.example.androidworks.data.model

data class ArtworkDetails(
    val objectId: Int,
    val title: String,
    val artistDisplayName: String,
    val artistDisplayBio: String,
    val objectDate: String,
    val medium: String,
    val dimensions: String,
    val classification: String,
    val department: String,
    val culture: String,
    val period: String,
    val dynasty: String,
    val country: String,
    val city: String,
    val creditLine: String,
    val primaryImage: String,
    val primaryImageSmall: String,
    val isPublicDomain: Boolean,
    val isHighlight: Boolean,
    val galleryNumber: String,
    val objectURL: String,
    val tags: List<String>
)