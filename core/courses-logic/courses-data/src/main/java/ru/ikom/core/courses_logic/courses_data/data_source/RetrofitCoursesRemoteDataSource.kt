package ru.ikom.core.courses_logic.courses_data.data_source

import retrofit2.Retrofit
import retrofit2.http.GET
import ru.ikom.core.courses_logic.courses_data.data_source.model.Courses

class RetrofitCoursesRemoteDataSource(
    private val retrofit: Retrofit
) : CoursesRemoteDataSource {

    private val service: RetrofitCoursesService by lazy {
        createRetrofitCoursesService(retrofit)
    }

    override suspend fun getCourses(): Courses {
        return service.getCourses()
    }
}

interface RetrofitCoursesService {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): Courses
}

private fun createRetrofitCoursesService(retrofit: Retrofit) =
    retrofit.create(RetrofitCoursesService::class.java)