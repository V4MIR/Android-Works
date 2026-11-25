package com.example.androidworks.domain.repository

import com.example.androidworks.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<Profile>
    suspend fun save(profile: Profile)
}