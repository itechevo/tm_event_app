package com.kupesan.tmapp.ui.model

data class EventUiModel(
    val id: String,
    val title: String,
    val date: String,
    val imageUrl: String?,
    val location: String?,
    val venue: String?
)