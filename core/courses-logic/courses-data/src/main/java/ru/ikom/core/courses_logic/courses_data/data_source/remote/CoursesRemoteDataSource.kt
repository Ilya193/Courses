package ru.ikom.core.courses_logic.courses_data.data_source.remote

import ru.ikom.core.courses_logic.courses_data.data_source.remote.model.Courses

interface CoursesRemoteDataSource {

    suspend fun getCourses(): Courses
}