package ru.ikom.core.courses_logic.courses_data.repository.mapper

import ru.ikom.core.courses_logic.courses_data.data_source.model.Course
import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain

interface CourseMapper {

    fun mapToDomain(data: Course): CourseDomain
}

class DefaultCourseMapper : CourseMapper {

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
}