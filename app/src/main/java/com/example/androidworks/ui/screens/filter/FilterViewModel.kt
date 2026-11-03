package com.example.androidworks.ui.screens.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.model.FilterSettings
import com.example.androidworks.domain.repository.FilterRepository
import com.example.androidworks.ui.badge.FilterBadgeCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: FilterRepository,
    private val badgeCache: FilterBadgeCache
) : ViewModel() {

    private val _state = MutableStateFlow(FilterSettings())
    val state: StateFlow<FilterSettings> = _state.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    init {
        viewModelScope.launch {
            repository.filters.collect { settings ->
                _state.value = settings
                badgeCache.update(settings.isNotDefault)
            }
        }
    }

    fun updateQuery(q: String) = _state.update { it.copy(query = q) }
    fun updateHasImages(v: Boolean) = _state.update { it.copy(hasImages = v) }
    fun updateDepartment(id: Int?) = _state.update { it.copy(departmentId = id) }

    fun save() {
        viewModelScope.launch {
            repository.save(_state.value)
            badgeCache.update(_state.value.isNotDefault)
            _saved.value = true
        }
    }

    fun consumeSaved() {
        _saved.value = false
    }
}