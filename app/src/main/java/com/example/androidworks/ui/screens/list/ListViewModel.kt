package com.example.androidworks.ui.screens.list

import androidx.lifecycle.ViewModel
import com.example.androidworks.data.MockData
import com.example.androidworks.data.model.Artwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel : ViewModel() {

    private val _artworks = MutableStateFlow<List<Artwork>>(emptyList())
    val artworks: StateFlow<List<Artwork>> = _artworks.asStateFlow()

    init {
        loadArtworks()
    }

    private fun loadArtworks() {
        _artworks.value = MockData.artworks
    }
}