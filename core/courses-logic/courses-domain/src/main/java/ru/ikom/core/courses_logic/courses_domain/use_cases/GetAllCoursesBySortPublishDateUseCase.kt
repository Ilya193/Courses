package ru.ikom.core.courses_logic.courses_domain.use_cases

import ru.ikom.core.courses_logic.courses_domain.CoursesRepository
import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain

class GetAllCoursesBySortPublishDateUseCase(
    private val coursesRepository: CoursesRepository
) {

    suspend operator fun invoke(): Result<List<CourseDomain>> {
        return coursesRepository.getCourses()
            .onSuccess { courses ->
                return Result.success(courses.sortedByDescending { it.publishDate })
            }
    }
}