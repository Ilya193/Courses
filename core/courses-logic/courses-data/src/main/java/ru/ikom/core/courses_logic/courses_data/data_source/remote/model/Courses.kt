package ru.ikom.core.courses_logic.courses_data.data_source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Courses(
    @SerialName("courses")
    val courses: List<Course>
)