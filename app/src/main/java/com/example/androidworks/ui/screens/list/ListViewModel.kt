package com.example.androidworks.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.usecase.SearchArtworksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val searchArtworksUseCase: SearchArtworksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("sunflowers")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        search()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun search() {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            val result = searchArtworksUseCase(
                query = _query.value,
                page = 0,
                pageSize = 20
            )
            _uiState.value = result.fold(
                onSuccess = { artworks ->
                    if (artworks.isEmpty()) ListUiState.Empty
                    else ListUiState.Success(artworks)
                },
                onFailure = { throwable ->
                    ListUiState.Error(
                        throwable.message ?: "Неизвестная ошибка загрузки"
                    )
                }
            )
        }
    }

    fun retry() = search()
}