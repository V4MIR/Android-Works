package com.example.androidworks.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.androidworks.data.repository.MuseumRepositoryImpl
import com.example.androidworks.domain.repository.MuseumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://collectionapi.metmuseum.org/"

    @Provides
    @Singleton
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMetApi(retrofit: Retrofit): MetApi =
        retrofit.create(MetApi::class.java)

    @Provides
    @Singleton
    fun provideMuseumRepository(api: MetApi): MuseumRepository =
        MuseumRepositoryImpl(api)
}