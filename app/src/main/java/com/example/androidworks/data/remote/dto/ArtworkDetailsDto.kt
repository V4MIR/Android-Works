package com.example.androidworks.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArtworkDetailsDto(
    @SerializedName("objectID") val objectId: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("artistDisplayName") val artistDisplayName: String?,
    @SerializedName("artistDisplayBio") val artistDisplayBio: String?,
    @SerializedName("objectDate") val objectDate: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("dimensions") val dimensions: String?,
    @SerializedName("classification") val classification: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("culture") val culture: String?,
    @SerializedName("period") val period: String?,
    @SerializedName("dynasty") val dynasty: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("creditLine") val creditLine: String?,
    @SerializedName("primaryImage") val primaryImage: String?,
    @SerializedName("primaryImageSmall") val primaryImageSmall: String?,
    @SerializedName("isPublicDomain") val isPublicDomain: Boolean?,
    @SerializedName("isHighlight") val isHighlight: Boolean?,
    @SerializedName("GalleryNumber") val galleryNumber: String?,
    @SerializedName("objectURL") val objectURL: String?,
    @SerializedName("tags") val tags: List<TagDto>?
)

data class TagDto(
    @SerializedName("term") val term: String?
)