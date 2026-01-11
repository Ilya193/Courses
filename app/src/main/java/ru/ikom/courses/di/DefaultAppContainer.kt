package ru.ikom.courses.di

import android.content.Context
import ru.ikom.feature.auth.impl.presentation.fragment.defaultAuthScreen
import ru.ikom.feature.courses.impl.presentation.component.CoursesDependencies
import ru.ikom.feature.courses.impl.presentation.fragment.defaultCoursesFeatureScreen
import ru.ikom.feature.main_menu.impl.presentation.component.MainMenuDependencies
import ru.ikom.feature.main_menu.impl.presentation.fragment.defaultMainMenuFeatureScreen
import ru.ikom.feature_root.RootFeatureScreen
import ru.ikom.feature_root.component.RootFeatureDependencies
import ru.ikom.feature_root.defaultRootFeatureScreen

class DefaultAppContainer(
    private val applicationContext: Context
) : AppContainer {

    private val coreModule: CoreModule by lazy {
        DefaultCoreModule()
    }

    private fun internalRootFeatureDependencies() =
        RootFeatureDependencies(
            authFeatureScreen = defaultAuthScreen(),
            mainMenuFeatureScreen = internalMainMenuFeatureScreen(),
        )

    private fun internalMainMenuFeatureScreen() =
        defaultMainMenuFeatureScreen(deps = ::internalMainMenuDependencies)

    private fun internalMainMenuDependencies() =
        MainMenuDependencies(
            coursesFeatureScreen = internalCoursesFeatureScreen()
        )

    private fun internalCoursesFeatureScreen() =
        defaultCoursesFeatureScreen(deps = ::internalCoursesDependencies)

    private fun internalCoursesDependencies() =
        CoursesDependencies(
            getAllCoursesUseCase = coreModule.coursesLogicModule.getAllCoursesUseCase,
            getAllCoursesBySortPublishDateUseCase = coreModule.coursesLogicModule.getAllCoursesBySortPublishDateUseCase,
        )

    override fun provideRootFeatureScreen(): RootFeatureScreen =
        defaultRootFeatureScreen(::internalRootFeatureDependencies)
}