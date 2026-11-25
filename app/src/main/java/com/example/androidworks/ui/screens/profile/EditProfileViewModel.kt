package com.example.androidworks.ui.screens.profile

import android.content.Context
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

    fun createCameraUri(): Uri {
        val file = File.createTempFile("avatar_", ".jpg", context.filesDir)
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    fun discardCameraPhoto(uri: Uri) {
        runCatching { context.contentResolver.delete(uri, null, null) }
    }

    fun importAvatar(uri: Uri) {
        viewModelScope.launch {
            val result = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                runCatching {
                    val file = File.createTempFile("avatar_", ".img", context.filesDir)
                    try {
                        val input = context.contentResolver.openInputStream(uri)
                            ?: error("Не удалось открыть фото")
                        input.use { source -> file.outputStream().use { source.copyTo(it) } }
                        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                    } catch (e: Exception) {
                        file.delete()
                        throw e
                    }
                }
            }
            result.onSuccess { updateAvatar(it.toString()) }.onFailure {
                android.widget.Toast.makeText(context, "Не удалось сохранить фото", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun save(onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            repository.save(_draft.value)
            onSaved()
        }
    }
}