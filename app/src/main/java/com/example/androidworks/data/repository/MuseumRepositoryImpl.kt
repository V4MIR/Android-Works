package com.example.androidworks.data.repository

import com.example.androidworks.data.remote.MetApi
import com.example.androidworks.data.remote.mapper.toDomain
import com.example.androidworks.domain.model.Artwork
import com.example.androidworks.domain.model.ArtworkDetails
import com.example.androidworks.domain.repository.MuseumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MuseumRepositoryImpl(
    private val api: MetApi
) : MuseumRepository {

    override suspend fun searchArtworks(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<Artwork>> = withContext(Dispatchers.IO) {
        runCatching {
            val offset = page * pageSize
            val response = api.searchArtworks(
                query = query,
                offset = offset,
                limit = pageSize
            )
            val ids = response.objectIds.orEmpty().take(pageSize)
            if (ids.isEmpty()) return@runCatching emptyList()

            ids.mapNotNull { id ->
                runCatching { api.getArtworkDetails(id) }
                    .getOrNull()
                    ?.toListArtwork()
            }
        }.recoverCatching { throwable ->
            throw when (throwable) {
                is IOException -> IOException("Нет соединения с интернетом", throwable)
                else -> throwable
            }
        }
    }

    override suspend fun getArtworkDetails(objectId: Int): Result<ArtworkDetails> =
        withContext(Dispatchers.IO) {
            runCatching {
                api.getArtworkDetails(objectId).toDomain()
            }.recoverCatching { throwable ->
                throw when (throwable) {
                    is IOException -> IOException("Нет соединения с интернетом", throwable)
                    else -> throwable
                }
            }
        }
}

private fun com.example.androidworks.data.remote.dto.ArtworkDetailsDto.toListArtwork(): Artwork =
    Artwork(
        objectId = objectId,
        title = title.orEmpty(),
        artistDisplayName = artistDisplayName.orEmpty(),
        primaryImageSmall = primaryImageSmall.orEmpty(),
        objectDate = objectDate.orEmpty(),
        department = department.orEmpty()
    )