package ru.ikom.core.courses_logic.courses_data.data_source

import ru.ikom.core.courses_logic.courses_data.data_source.model.Courses

interface CoursesRemoteDataSource {

    suspend fun getCourses(): Courses
}