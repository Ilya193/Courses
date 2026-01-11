package ru.ikom.core.courses_logic.courses_data.data_source.local.model

data class CourseCache(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)