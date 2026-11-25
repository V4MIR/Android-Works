package com.example.androidworks.domain.model

data class Profile(
    val fullName: String = "",
    val position: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = ""
) {
    val isEmpty: Boolean
        get() = fullName.isBlank() && avatarUri.isBlank() && resumeUrl.isBlank()
}