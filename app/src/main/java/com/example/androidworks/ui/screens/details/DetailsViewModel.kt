package com.example.androidworks.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.model.Artwork
import com.example.androidworks.domain.repository.FavoriteRepository
import com.example.androidworks.domain.usecase.GetArtworkDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getArtworkDetailsUseCase: GetArtworkDetailsUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var current: Artwork? = null
    private var currentId: Int = -1

    fun load(objectId: Int) {
        if (currentId == objectId && _uiState.value is DetailsUiState.Success) return
        currentId = objectId
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            val result = getArtworkDetailsUseCase(objectId)
            _uiState.value = result.fold(
                onSuccess = { details ->
                    current = Artwork(
                        objectId = details.objectId,
                        title = details.title,
                        artistDisplayName = details.artistDisplayName,
                        primaryImageSmall = details.primaryImageSmall,
                        objectDate = details.objectDate,
                        department = details.department
                    )
                    _isFavorite.value = favoriteRepository.isFavorite(details.objectId)
                    DetailsUiState.Success(details)
                },
                onFailure = { DetailsUiState.Error(it.message ?: "Ошибка") }
            )
        }
    }

    fun toggleFavorite() {
        val artwork = current ?: return
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.remove(artwork.objectId)
                _isFavorite.value = false
            } else {
                favoriteRepository.add(artwork)
                _isFavorite.value = true
            }
        }
    }

    fun retry() = if (currentId > 0) load(currentId) else Unit
}