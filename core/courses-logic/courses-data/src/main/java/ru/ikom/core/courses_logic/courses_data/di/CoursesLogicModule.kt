package ru.ikom.core.courses_logic.courses_data.di

import retrofit2.Retrofit
import ru.ikom.core.common.platform.AppDispatchers
import ru.ikom.core.courses_logic.courses_data.data_source.RetrofitCoursesRemoteDataSource
import ru.ikom.core.courses_logic.courses_data.repository.DefaultCoursesRepository
import ru.ikom.core.courses_logic.courses_data.repository.mapper.DefaultCourseMapper
import ru.ikom.core.courses_logic.courses_domain.CoursesRepository

interface CoursesLogicModule {
    val coursesRepository: CoursesRepository
}

fun CoursesLogicModule(
    retrofit: Retrofit,
    appDispatchers: AppDispatchers,
) =
    object : CoursesLogicModule {
        override val coursesRepository: CoursesRepository =
            DefaultCoursesRepository(
                coursesRemoteDataSource = RetrofitCoursesRemoteDataSource(retrofit),
                appDispatchers = appDispatchers,
                courseMapper = DefaultCourseMapper()
            )
    }