package ru.ikom.core.courses_logic.courses_data.repository.mapper

import ru.ikom.core.courses_logic.courses_data.data_source.local.model.CourseCache
import ru.ikom.core.courses_logic.courses_data.data_source.remote.model.Course
import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain

interface CourseMapper {

    fun mapToCache(data: Course): CourseCache

    fun mapToDomain(data: Course): CourseDomain

    fun mapToDomain(data: CourseCache): CourseDomain
}

class DefaultCourseMapper : CourseMapper {

    override fun mapToCache(data: Course): CourseCache =
        CourseCache(
            id = data.id,
            title = data.title,
            text = data.text,
            price = data.price,
            rate = data.rate,
            startDate = data.startDate,
            hasLike = data.hasLike,
            publishDate = data.publishDate
        )

    override fun mapToDomain(data: Course): CourseDomain =
        CourseDomain(
            id = data.id,
            title = data.title,
            text = data.text,
            price = data.price,
            rate = data.rate,
            startDate = data.startDate,
            hasLike = data.hasLike,
            publishDate = data.publishDate
        )

    override fun mapToDomain(data: CourseCache): CourseDomain =
        CourseDomain(
            id = data.id,
            title = data.title,
            text = data.text,
            price = data.price,
            rate = data.rate,
            startDate = data.startDate,
            hasLike = data.hasLike,
            publishDate = data.publishDate
        )
}