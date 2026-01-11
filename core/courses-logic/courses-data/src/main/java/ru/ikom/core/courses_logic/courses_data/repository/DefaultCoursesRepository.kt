package ru.ikom.core.courses_logic.courses_data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import ru.ikom.core.common.platform.AppDispatchers
import ru.ikom.core.courses_logic.courses_data.data_source.CoursesRemoteDataSource
import ru.ikom.core.courses_logic.courses_data.repository.mapper.CourseMapper
import ru.ikom.core.courses_logic.courses_domain.CoursesRepository
import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain

class DefaultCoursesRepository(
    private val coursesRemoteDataSource: CoursesRemoteDataSource,
    private val appDispatchers: AppDispatchers,
    private val courseMapper: CourseMapper,
) : CoursesRepository {

    override suspend fun getCourses(): Result<List<CourseDomain>> {
        return withContext(appDispatchers.io) {
            try {
                val resultFromRemote = coursesRemoteDataSource.getCourses()

                val map = resultFromRemote.courses.map(courseMapper::mapToDomain)

                return@withContext Result.success(map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }
}