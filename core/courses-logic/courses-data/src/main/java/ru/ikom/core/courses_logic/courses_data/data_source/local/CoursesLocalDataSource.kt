package ru.ikom.core.courses_logic.courses_data.data_source.local

import ru.ikom.core.courses_logic.courses_data.data_source.local.model.CourseCache

interface CoursesLocalDataSource {
    suspend fun saveCourses(courses: List<CourseCache>)
    suspend fun getCourses(): List<CourseCache>
}