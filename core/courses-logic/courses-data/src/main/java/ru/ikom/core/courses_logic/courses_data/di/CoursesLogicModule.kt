package ru.ikom.core.courses_logic.courses_data.di

import retrofit2.Retrofit
import ru.ikom.core.common.platform.AppDispatchers
import ru.ikom.core.courses_logic.courses_data.data_source.local.InMemoryCoursesLocalDataSource
import ru.ikom.core.courses_logic.courses_data.data_source.remote.RetrofitCoursesRemoteDataSource
import ru.ikom.core.courses_logic.courses_data.repository.DefaultCoursesRepository
import ru.ikom.core.courses_logic.courses_data.repository.mapper.DefaultCourseMapper
import ru.ikom.core.courses_logic.courses_domain.CoursesRepository
import ru.ikom.core.courses_logic.courses_domain.use_cases.GetAllCoursesBySortPublishDateUseCase
import ru.ikom.core.courses_logic.courses_domain.use_cases.GetAllCoursesUseCase

interface CoursesLogicModule {
    val coursesRepository: CoursesRepository
    val getAllCoursesUseCase: GetAllCoursesUseCase
    val getAllCoursesBySortPublishDateUseCase: GetAllCoursesBySortPublishDateUseCase
}

fun CoursesLogicModule(
    retrofit: Retrofit,
    appDispatchers: AppDispatchers,
) =
    object : CoursesLogicModule {

        override val coursesRepository: CoursesRepository =
            DefaultCoursesRepository(
                coursesRemoteDataSource = RetrofitCoursesRemoteDataSource(retrofit),
                coursesLocalDataSource = InMemoryCoursesLocalDataSource(),
                appDispatchers = appDispatchers,
                courseMapper = DefaultCourseMapper()
            )
        override val getAllCoursesUseCase: GetAllCoursesUseCase =
            GetAllCoursesUseCase(coursesRepository)

        override val getAllCoursesBySortPublishDateUseCase: GetAllCoursesBySortPublishDateUseCase =
            GetAllCoursesBySortPublishDateUseCase(coursesRepository)
    }