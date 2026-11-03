package com.example.androidworks.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidworks.domain.model.FilterSettings
import com.example.androidworks.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("filter_prefs")

@Singleton
class FilterPreferences @Inject constructor(
    private val context: Context
) : FilterRepository {

    private object Keys {
        val QUERY = stringPreferencesKey("query")
        val HAS_IMAGES = booleanPreferencesKey("has_images")
        val DEPARTMENT = intPreferencesKey("department")
    }

    override val filters: Flow<FilterSettings> = context.dataStore.data
        .catch { throwable ->
            if (throwable is IOException) emit(emptyPreferences())
            else throw throwable
        }
        .map { prefs ->
            FilterSettings(
                query = prefs[Keys.QUERY] ?: "sunflowers",
                hasImages = prefs[Keys.HAS_IMAGES] ?: true,
                departmentId = prefs[Keys.DEPARTMENT]
            )
        }

    override suspend fun save(settings: FilterSettings) {
        context.dataStore.edit { prefs ->
            prefs[Keys.QUERY] = settings.query
            prefs[Keys.HAS_IMAGES] = settings.hasImages
            val dept = settings.departmentId
            if (dept != null) prefs[Keys.DEPARTMENT] = dept
            else prefs.remove(Keys.DEPARTMENT)
        }
    }
}