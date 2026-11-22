package com.example.androidworks.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("total") val total: Int,
    @SerializedName("objectIDs") val objectIds: List<Int>?
)