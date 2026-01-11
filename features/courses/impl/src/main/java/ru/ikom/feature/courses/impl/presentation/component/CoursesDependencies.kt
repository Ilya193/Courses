package ru.ikom.feature.courses.impl.presentation.component

import ru.ikom.core.courses_logic.courses_domain.CoursesRepository

interface CoursesDependencies {
    val coursesRepository: CoursesRepository
}

fun CoursesDependencies(
    coursesRepository: CoursesRepository
) =
    object : CoursesDependencies {
        override val coursesRepository: CoursesRepository = coursesRepository
    }