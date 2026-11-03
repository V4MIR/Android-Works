package com.example.androidworks.di

import com.example.androidworks.ui.badge.FilterBadgeCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BadgeModule {

    @Provides
    @Singleton
    fun provideFilterBadgeCache(): FilterBadgeCache = FilterBadgeCache()
}