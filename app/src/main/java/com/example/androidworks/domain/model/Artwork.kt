package com.example.androidworks.domain.model

data class Artwork(
    val objectId: Int,
    val title: String,
    val artistDisplayName: String,
    val primaryImageSmall: String,
    val objectDate: String,
    val department: String
)