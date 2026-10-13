package com.example.androidworks.domain.repository

import com.example.androidworks.domain.model.Artwork
import com.example.androidworks.domain.model.ArtworkDetails

interface MuseumRepository {
    suspend fun searchArtworks(
        query: String,
        page: Int = 0,
        pageSize: Int = 20
    ): Result<List<Artwork>>

    suspend fun getArtworkDetails(objectId: Int): Result<ArtworkDetails>
}