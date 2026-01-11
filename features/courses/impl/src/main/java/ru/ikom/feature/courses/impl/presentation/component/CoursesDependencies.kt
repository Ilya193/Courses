package ru.ikom.feature.courses.impl.presentation.component

import ru.ikom.core.courses_logic.courses_domain.use_cases.GetAllCoursesBySortPublishDateUseCase
import ru.ikom.core.courses_logic.courses_domain.use_cases.GetAllCoursesUseCase

interface CoursesDependencies {
    val getAllCoursesUseCase: GetAllCoursesUseCase
    val getAllCoursesBySortPublishDateUseCase: GetAllCoursesBySortPublishDateUseCase
}

fun CoursesDependencies(
    getAllCoursesUseCase: GetAllCoursesUseCase,
    getAllCoursesBySortPublishDateUseCase: GetAllCoursesBySortPublishDateUseCase
) =
    object : CoursesDependencies {
        override val getAllCoursesUseCase: GetAllCoursesUseCase = getAllCoursesUseCase
        override val getAllCoursesBySortPublishDateUseCase: GetAllCoursesBySortPublishDateUseCase =
            getAllCoursesBySortPublishDateUseCase
    }