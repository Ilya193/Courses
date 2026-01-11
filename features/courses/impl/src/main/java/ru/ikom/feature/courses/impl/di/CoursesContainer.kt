package ru.ikom.feature.courses.impl.di

import ru.ikom.feature.courses.impl.presentation.component.mappers.CourseMapper
import ru.ikom.feature.courses.impl.presentation.component.mappers.DefaultCourseMapper

interface CoursesContainer {

    val courseMapper: CourseMapper
}

fun CoursesContainer() =
    object : CoursesContainer {
        override val courseMapper: CourseMapper = DefaultCourseMapper()
    }