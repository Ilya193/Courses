package ru.ikom.feature.main_menu.impl.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature.courses.api.CoursesFeature
import ru.ikom.feature.main_menu.api.MainMenuFeature

class MainMenuComponent(
    private val feature: MainMenuFeature,
    private val mainMenuDependencies: MainMenuDependencies,
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(mainMenuDependencies.coursesFeatureScreen.launch())
    }

    fun coursesFeature() =
        object : CoursesFeature {

        }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val feature: () -> MainMenuFeature,
        private val deps: MainMenuDependencies,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainMenuComponent(feature(), deps) as T
        }
    }
}