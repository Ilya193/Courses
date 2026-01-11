package ru.ikom.feature.main_menu.impl.presentation.component

import ru.ikom.feature.courses.api.CoursesFeatureScreen

interface MainMenuDependencies {
    val coursesFeatureScreen: CoursesFeatureScreen
}

fun MainMenuDependencies(
    coursesFeatureScreen: CoursesFeatureScreen
) =
    object : MainMenuDependencies {
        override val coursesFeatureScreen: CoursesFeatureScreen = coursesFeatureScreen
    }