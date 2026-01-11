package ru.ikom.courses.di

import ru.ikom.core.common.platform.AppDispatchers
import ru.ikom.core.courses_logic.courses_data.di.CoursesLogicModule
import ru.ikom.core.courses_logic.courses_domain.CoursesRepository
import ru.ikom.core.network.CoreNetworkModule
import ru.ikom.courses.di.platform.AndroidAppDispatchers

interface CoreModule {

    val appDispatchers: AppDispatchers

    val coreNetworkModule: CoreNetworkModule

    val coursesRepository: CoursesRepository
}

class DefaultCoreModule : CoreModule {

    override val appDispatchers: AppDispatchers by lazy {
        AndroidAppDispatchers()
    }

    override val coreNetworkModule: CoreNetworkModule by lazy {
        CoreNetworkModule()
    }

    private val coursesLogicModule by lazy {
        CoursesLogicModule(
            retrofit = coreNetworkModule.retrofit,
            appDispatchers = appDispatchers
        )
    }

    override val coursesRepository: CoursesRepository
        get() = coursesLogicModule.coursesRepository
}