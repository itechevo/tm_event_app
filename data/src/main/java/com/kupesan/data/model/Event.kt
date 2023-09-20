package com.kupesan.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey
    val id: String,
    val title: String,
    val date: String?,
    val imageUrl: String?,
    val countryCode: String,
    val location: String?,
    val venue: String?
)
