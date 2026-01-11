package ru.ikom.core.courses_logic.courses_data.data_source.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.ikom.core.courses_logic.courses_data.data_source.local.model.CourseCache

class InMemoryCoursesLocalDataSource : CoursesLocalDataSource {

    private val storage = MutableStateFlow<List<CourseCache>>(emptyList())

    override suspend fun saveCourses(courses: List<CourseCache>) {
        storage.update { courses }
    }

    override suspend fun getCourses(): List<CourseCache> {
        return storage.value
    }
}