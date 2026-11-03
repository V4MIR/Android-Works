package com.example.androidworks.domain.repository

import com.example.androidworks.domain.model.FilterSettings
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    val filters: Flow<FilterSettings>

    suspend fun save(settings: FilterSettings)
}