package com.example.androidworks.data.remote

import com.example.androidworks.data.remote.dto.ArtworkDetailsDto
import com.example.androidworks.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetApi {

    @GET("public/collection/v1.1/search")
    suspend fun searchArtworks(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("hasImages") hasImages: Boolean = true
    ): SearchResponseDto

    @GET("public/collection/v1/objects/{objectID}")
    suspend fun getArtworkDetails(
        @Path("objectID") objectId: Int
    ): ArtworkDetailsDto
}