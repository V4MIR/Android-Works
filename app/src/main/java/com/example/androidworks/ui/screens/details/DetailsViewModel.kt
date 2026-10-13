package com.example.androidworks.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.usecase.GetArtworkDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getArtworkDetailsUseCase: GetArtworkDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private var objectId: Int = -1

    fun load(id: Int) {
        if (objectId == id && _uiState.value is DetailsUiState.Success) return
        objectId = id
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            val result = getArtworkDetailsUseCase(id)
            _uiState.value = result.fold(
                onSuccess = { DetailsUiState.Success(it) },
                onFailure = { DetailsUiState.Error(it.message ?: "Ошибка загрузки") }
            )
        }
    }

    fun retry() {
        if (objectId > 0) load(objectId)
    }
}