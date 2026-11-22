package com.example.androidworks.ui.badge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BadgeViewModel @Inject constructor(
    cache: FilterBadgeCache
) : ViewModel() {
    val showBadge: StateFlow<Boolean> = cache.showBadge
}