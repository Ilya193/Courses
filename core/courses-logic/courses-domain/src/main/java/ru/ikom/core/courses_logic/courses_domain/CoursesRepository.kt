package ru.ikom.core.courses_logic.courses_domain

import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain

interface CoursesRepository {

    suspend fun getCourses(): Result<List<CourseDomain>>
}