package com.example.androidworks.ui.screens.details

import com.example.androidworks.domain.model.ArtworkDetails

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val details: ArtworkDetails) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}