package com.example.androidworks.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.androidworks.data.repository.MuseumRepositoryImpl
import com.example.androidworks.domain.repository.MuseumRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

    private const val BASE_URL = "https://collectionapi.metmuseum.org/"

    @Volatile
    private var repository: MuseumRepository? = null

    fun provideRepository(context: Context): MuseumRepository {
        return repository ?: synchronized(this) {
            repository ?: createRepository(context).also { repository = it }
        }
    }

    private fun createRepository(context: Context): MuseumRepository {
        val okHttp = OkHttpClient.Builder()
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

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(MetApi::class.java)
        return MuseumRepositoryImpl(api)
    }
}