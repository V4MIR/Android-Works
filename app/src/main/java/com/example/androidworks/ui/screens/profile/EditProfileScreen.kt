package com.example.androidworks.ui.screens.profile

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Surface
import androidx.compose.material3.ListItem
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onDone: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val draft by viewModel.draft.collectAsState()
    var showSourceDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var pendingPhoto by rememberSaveable { mutableStateOf<String?>(null) }
    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) viewModel.importAvatar(uri)
    }
    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        pendingPhoto?.let { value ->
            if (success) viewModel.updateAvatar(value)
            else viewModel.discardCameraPhoto(Uri.parse(value))
        }
        pendingPhoto = null
    }

    fun launchCamera() {
        try {
            val uri = viewModel.createCameraUri()
            pendingPhoto = uri.toString()
            takePicture.launch(uri)
        } catch (e: Exception) {
            pendingPhoto?.let { viewModel.discardCameraPhoto(Uri.parse(it)) }
            pendingPhoto = null
            Toast.makeText(context, "Не удалось открыть камеру", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование профиля") },
                actions = {
                    TextButton(onClick = { viewModel.save { onDone() } }) {
                        Text("Готово", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (draft.avatarUri.isNotBlank()) {
                AsyncImage(
                    model = draft.avatarUri,
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { showSourceDialog = true }
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Аватар",
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { showSourceDialog = true },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = draft.fullName,
                onValueChange = viewModel::updateName,
                label = { Text("ФИО") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = draft.position,
                onValueChange = viewModel::updatePosition,
                label = { Text("Должность") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = draft.resumeUrl,
                onValueChange = viewModel::updateResume,
                label = { Text("Ссылка на резюме (PDF)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { viewModel.save { onDone() } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Готово")
            }
        }
    }

    if (showSourceDialog) {
        AvatarSourceDialog(
            onDismiss = { showSourceDialog = false },
            onGallery = {
                showSourceDialog = false
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            onCamera = {
                showSourceDialog = false
                launchCamera()
            }
        )
    }
}

@Composable
private fun AvatarSourceDialog(onDismiss: () -> Unit, onGallery: () -> Unit, onCamera: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.large) {
            Column(Modifier.padding(16.dp)) {
                Text("Новая аватарка", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                ListItem(headlineContent = { Text("Галерея") }, modifier = Modifier.clickable(onClick = onGallery))
                ListItem(headlineContent = { Text("Камера") }, modifier = Modifier.clickable(onClick = onCamera))
                TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) { Text("Отмена") }
            }
        }
    }
}
