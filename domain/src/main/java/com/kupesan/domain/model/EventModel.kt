package com.kupesan.domain.model

data class EventModel(
    val id: String,
    val title: String,
    val date: String?,
    val imageUrl: String?,
    val location: String?,
    val venue: String?
)