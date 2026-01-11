package ru.ikom.feature.courses.impl.presentation.component.mappers

import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain
import ru.ikom.feature.courses.impl.presentation.component.model.CourseUi

interface CourseMapper {

    fun mapToUi(data: CourseDomain): CourseUi
}

class DefaultCourseMapper : CourseMapper {

    override fun mapToUi(data: CourseDomain): CourseUi =
        CourseUi(
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