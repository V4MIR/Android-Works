package com.example.androidworks.di

import com.example.androidworks.data.local.prefs.ProfilePreferences
import com.example.androidworks.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfilePreferences
    ): ProfileRepository
}