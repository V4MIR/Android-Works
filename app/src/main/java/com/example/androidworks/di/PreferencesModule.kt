package com.example.androidworks.di

import android.content.Context
import com.example.androidworks.data.local.prefs.FilterPreferences
import com.example.androidworks.domain.repository.FilterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideFilterRepository(
        @ApplicationContext context: Context
    ): FilterRepository = FilterPreferences(context)
}