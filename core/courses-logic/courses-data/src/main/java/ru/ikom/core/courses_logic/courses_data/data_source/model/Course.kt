package ru.ikom.core.courses_logic.courses_data.data_source.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)