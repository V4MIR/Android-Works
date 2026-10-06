package com.example.androidworks.ui.screens.details

import androidx.lifecycle.ViewModel
import com.example.androidworks.data.MockData
import com.example.androidworks.data.model.ArtworkDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel : ViewModel() {

    private val _details = MutableStateFlow<ArtworkDetails?>(null)
    val details: StateFlow<ArtworkDetails?> = _details.asStateFlow()

    fun load(objectId: Int) {
        _details.value = MockData.getDetailsById(objectId)
    }
}