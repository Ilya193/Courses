package ru.ikom.feature.auth.api

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseAuthFragment(layout: Int) : Fragment(layout)

interface AuthFeatureLaunch {
    fun launch(): FragmentScreen
}

interface AuthFeatureContent {
    val tag: String
    fun content(feature: () -> AuthFeature): BaseAuthFragment
}

interface AuthFeatureScreen : AuthFeatureContent, AuthFeatureLaunch