package com.example.androidworks.data.local.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidworks.domain.model.Profile
import com.example.androidworks.domain.repository.ProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.profileDataStore by preferencesDataStore("profile_prefs")

@Singleton
class ProfilePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileRepository {

    private object Keys {
        val NAME = stringPreferencesKey("full_name")
        val POSITION = stringPreferencesKey("position")
        val AVATAR = stringPreferencesKey("avatar_uri")
        val RESUME = stringPreferencesKey("resume_url")
    }

    override val profile: Flow<Profile> = context.profileDataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs ->
            Profile(
                fullName = prefs[Keys.NAME] ?: "",
                position = prefs[Keys.POSITION] ?: "",
                avatarUri = prefs[Keys.AVATAR] ?: "",
                resumeUrl = prefs[Keys.RESUME] ?: ""
            )
        }

    override suspend fun save(profile: Profile) {
        context.profileDataStore.edit { prefs ->
            prefs[Keys.NAME] = profile.fullName
            prefs[Keys.POSITION] = profile.position
            prefs[Keys.AVATAR] = profile.avatarUri
            prefs[Keys.RESUME] = profile.resumeUrl
        }
    }
}