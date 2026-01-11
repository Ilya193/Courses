package ru.ikom.core.courses_logic.courses_data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import ru.ikom.core.common.platform.AppDispatchers
import ru.ikom.core.courses_logic.courses_data.data_source.local.CoursesLocalDataSource
import ru.ikom.core.courses_logic.courses_data.data_source.remote.CoursesRemoteDataSource
import ru.ikom.core.courses_logic.courses_data.repository.mapper.CourseMapper
import ru.ikom.core.courses_logic.courses_domain.CoursesRepository
import ru.ikom.core.courses_logic.courses_domain.model.CourseDomain

class DefaultCoursesRepository(
    private val coursesRemoteDataSource: CoursesRemoteDataSource,
    private val coursesLocalDataSource: CoursesLocalDataSource,
    private val appDispatchers: AppDispatchers,
    private val courseMapper: CourseMapper,
) : CoursesRepository {

    override suspend fun getCourses(): Result<List<CourseDomain>> {
        return withContext(appDispatchers.io) {
            try {
                val cacheCourses = coursesLocalDataSource.getCourses()

                if (cacheCourses.isNotEmpty()) {
                    val finalCourses = cacheCourses.map(courseMapper::mapToDomain)
                    return@withContext Result.success(finalCourses)
                }

                val resultFromRemote = coursesRemoteDataSource.getCourses()

                val cacheCoursesForSave = resultFromRemote.courses.map(courseMapper::mapToCache)
                coursesLocalDataSource.saveCourses(cacheCoursesForSave)

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