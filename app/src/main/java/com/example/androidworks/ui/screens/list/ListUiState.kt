package com.example.androidworks.ui.screens.list

import com.example.androidworks.domain.model.Artwork

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Success(val artworks: List<Artwork>) : ListUiState
    data class Error(val message: String) : ListUiState
    data object Empty : ListUiState
}