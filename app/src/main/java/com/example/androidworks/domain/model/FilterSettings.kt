package com.example.androidworks.domain.model

data class FilterSettings(
    val query: String = "sunflowers",
    val hasImages: Boolean = true,
    val departmentId: Int? = null
) {
    val isNotDefault: Boolean
        get() = this != FilterSettings()
}