package com.example.androidworks.data.local.db

import com.example.androidworks.domain.model.Artwork
import com.example.androidworks.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoriteRepository {

    override fun observeFavorites(): Flow<List<Artwork>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun add(artwork: Artwork) {
        dao.insert(artwork.toEntity())
    }

    override suspend fun remove(objectId: Int) = dao.delete(objectId)

    override suspend fun isFavorite(objectId: Int): Boolean = dao.exists(objectId)
}

private fun FavoriteEntity.toDomain() = Artwork(
    objectId = objectId,
    title = title,
    artistDisplayName = artistDisplayName,
    primaryImageSmall = primaryImageSmall,
    objectDate = objectDate,
    department = department
)

private fun Artwork.toEntity() = FavoriteEntity(
    objectId = objectId,
    title = title,
    artistDisplayName = artistDisplayName,
    primaryImageSmall = primaryImageSmall,
    objectDate = objectDate,
    department = department
)