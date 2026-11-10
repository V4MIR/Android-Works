package com.example.androidworks.di

import android.content.Context
import androidx.room.Room
import com.example.androidworks.data.local.db.AppDatabase
import com.example.androidworks.data.local.db.FavoriteDao
import com.example.androidworks.data.local.db.FavoriteRepositoryImpl
import com.example.androidworks.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "museum.db").build()

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun provideFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository = impl
}