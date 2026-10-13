package com.example.androidworks.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidworks.data.remote.NetworkModule
import com.example.androidworks.domain.usecase.GetArtworkDetailsUseCase
import com.example.androidworks.domain.usecase.SearchArtworksUseCase
import com.example.androidworks.ui.screens.details.DetailsViewModel
import com.example.androidworks.ui.screens.list.ListViewModel

class AppViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = NetworkModule.provideRepository(context)
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) ->
                ListViewModel(SearchArtworksUseCase(repository)) as T
            modelClass.isAssignableFrom(DetailsViewModel::class.java) ->
                DetailsViewModel(GetArtworkDetailsUseCase(repository)) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}