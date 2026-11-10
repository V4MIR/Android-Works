package com.example.androidworks.domain.repository

import com.example.androidworks.domain.model.Artwork
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavorites(): Flow<List<Artwork>>
    suspend fun add(artwork: Artwork)
    suspend fun remove(objectId: Int)
    suspend fun isFavorite(objectId: Int): Boolean
}