package com.example.androidworks.domain.usecase


import com.example.androidworks.domain.model.ArtworkDetails
import com.example.androidworks.domain.repository.MuseumRepository

class GetArtworkDetailsUseCase(
    private val repository: MuseumRepository
) {
    suspend operator fun invoke(objectId: Int): Result<ArtworkDetails> {
        if (objectId <= 0) {
            return Result.failure(IllegalArgumentException("Некорректный objectId: $objectId"))
        }
        return repository.getArtworkDetails(objectId)
    }
}