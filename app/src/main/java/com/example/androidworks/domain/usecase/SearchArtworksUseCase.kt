package com.example.androidworks.domain.usecase

import com.example.androidworks.domain.model.Artwork
import com.example.androidworks.domain.repository.MuseumRepository

class SearchArtworksUseCase(
    private val repository: MuseumRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 0,
        pageSize: Int = 20
    ): Result<List<Artwork>> {
        if (query.isBlank()) {
            return Result.failure(IllegalArgumentException("Поисковый запрос не может быть пустым"))
        }
        return repository.searchArtworks(query, page, pageSize)
    }
}