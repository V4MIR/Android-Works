package com.example.androidworks.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.repository.FilterRepository
import com.example.androidworks.domain.usecase.SearchArtworksUseCase
import com.example.androidworks.ui.badge.FilterBadgeCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val searchArtworksUseCase: SearchArtworksUseCase,
    private val filterRepository: FilterRepository,
    private val badgeCache: FilterBadgeCache
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()
    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            val filters = filterRepository.filters.first()
            badgeCache.update(filters.isNotDefault)
            val result = searchArtworksUseCase(
                query = filters.query,
                page = 0,
                pageSize = 20,
                hasImages = filters.hasImages
            )
            _uiState.value = result.fold(
                onSuccess = { list ->
                    if (list.isEmpty()) ListUiState.Empty
                    else ListUiState.Success(list)
                },
                onFailure = { e ->
                    ListUiState.Error(e.message ?: "Ошибка загрузки")
                }
            )
        }
    }

    fun retry() = load()
}