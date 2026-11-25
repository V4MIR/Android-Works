package com.example.androidworks.ui.screens.profile

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidworks.domain.model.Profile
import com.example.androidworks.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val profile: StateFlow<Profile> = repository.profile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Profile()
        )

    private val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    private var downloadId: Long? = null
    private var downloadedUrl: String? = null
    private var downloadedUri: Uri? = null
    private var receiverRegistered = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE &&
                intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId) {
                finishDownload()
            }
        }
    }

    fun downloadResume(url: String) {
        if (downloadId != null) {
            finishDownload()
            if (downloadId != null) message("Резюме скачивается")
            return
        }
        if (downloadedUrl == url && downloadedUri != null) {
            openResume(downloadedUri!!)
            return
        }
        try {
            val uri = Uri.parse(url)
            require(uri.scheme in listOf("http", "https") && !uri.host.isNullOrBlank())
            if (!receiverRegistered) {
                ContextCompat.registerReceiver(context, receiver,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                    ContextCompat.RECEIVER_EXPORTED)
                receiverRegistered = true
            }
            val request = DownloadManager.Request(uri)
                .setTitle("Резюме")
                .setDescription("Скачивание PDF")
                .setMimeType("application/pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            downloadId = dm.enqueue(request)
            downloadedUrl = url
            downloadedUri = null
            finishDownload()
        } catch (e: Exception) {
            downloadId = null
            message("Не удалось начать скачивание резюме")
        }
    }

    private fun finishDownload() {
        val id = downloadId ?: return
        try {
            dm.query(DownloadManager.Query().setFilterById(id)).use { cursor ->
                if (!cursor.moveToFirst()) {
                    downloadId = null
                    message("Загрузка не найдена")
                    return
                }
                when (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        downloadId = null
                        downloadedUri = dm.getUriForDownloadedFile(id)
                        downloadedUri?.let(::openResume) ?: message("Файл резюме недоступен")
                    }
                    DownloadManager.STATUS_FAILED -> {
                        downloadId = null
                        val reason = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON))
                        message("Не удалось скачать резюме (код $reason)")
                    }
                }
            }
        } catch (e: Exception) {
            downloadId = null
            message("Не удалось получить скачанный файл")
        }
    }

    private fun openResume(uri: Uri) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } catch (e: Exception) {
            message("Не удалось открыть PDF. Проверьте наличие приложения для просмотра PDF")
        }
    }

    private fun message(text: String) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    override fun onCleared() {
        if (receiverRegistered) context.unregisterReceiver(receiver)
        super.onCleared()
    }
}
