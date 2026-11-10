package com.example.androidworks.ui.screens.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.model.Profile
import com.example.androidworks.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _draft = MutableStateFlow(Profile())
    val draft: StateFlow<Profile> = _draft.asStateFlow()

    init {
        viewModelScope.launch {
            _draft.value = repository.profile.first()
        }
    }

    fun updateName(v: String) = _draft.update { it.copy(fullName = v) }

    fun updatePosition(v: String) = _draft.update { it.copy(position = v) }

    fun updateResume(v: String) = _draft.update { it.copy(resumeUrl = v) }

    fun updateAvatar(uri: String) = _draft.update { it.copy(avatarUri = uri) }

    fun saveBitmapToInternalStorage(bitmap: Bitmap): Uri {
        val file = File(context.filesDir, "avatar_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun save(onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            repository.save(_draft.value)
            onSaved()
        }
    }
}