package ru.ikom.feature.main_menu.api

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseMainMenuFragment(layout: Int) : Fragment(layout)

interface MainMenuFeatureLaunch {
    fun launch(): FragmentScreen
}

interface MainMenuFeatureContent {
    val tag: String
    fun content(feature: () -> MainMenuFeature): BaseMainMenuFragment
}

interface MainMenuFeatureScreen : MainMenuFeatureContent, MainMenuFeatureLaunch