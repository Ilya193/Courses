package ru.ikom.feature_root.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature.auth.api.AuthFeature
import ru.ikom.feature.main_menu.api.MainMenuFeature

class RootComponent(
    private val feature: RootFeature,
    private val rootFeatureDependencies: RootFeatureDependencies
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(rootFeatureDependencies.authFeatureScreen.launch())
    }

    private fun internalOnOpenMainMenu() {
        router.newRootScreen(rootFeatureDependencies.mainMenuFeatureScreen.launch())
    }

    fun authFeature() =
        object : AuthFeature {
            override fun userIsAuthorized() = internalOnOpenMainMenu()
        }

    fun mainMenuFeature() =
        object : MainMenuFeature {

        }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val feature: () -> RootFeature,
        private val rootFeatureDependencies: RootFeatureDependencies
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RootComponent(feature(), rootFeatureDependencies) as T
        }
    }
}