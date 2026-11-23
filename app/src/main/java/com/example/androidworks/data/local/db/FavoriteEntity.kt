package com.example.androidworks.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val objectId: Int,
    val title: String,
    val artistDisplayName: String,
    val primaryImageSmall: String,
    val objectDate: String,
    val department: String,
    val savedAt: Long = System.currentTimeMillis()
)