package ru.ikom.feature_root.component

import ru.ikom.feature.auth.api.AuthFeatureScreen
import ru.ikom.feature.main_menu.api.MainMenuFeatureScreen

interface RootFeatureDependencies {
    val authFeatureScreen: AuthFeatureScreen
    val mainMenuFeatureScreen: MainMenuFeatureScreen
}

fun RootFeatureDependencies(
    authFeatureScreen: AuthFeatureScreen,
    mainMenuFeatureScreen: MainMenuFeatureScreen
) =
    object : RootFeatureDependencies {
        override val authFeatureScreen: AuthFeatureScreen = authFeatureScreen
        override val mainMenuFeatureScreen: MainMenuFeatureScreen = mainMenuFeatureScreen
    }