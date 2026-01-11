package ru.ikom.feature.courses.api

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

abstract class BaseCoursesFragment(layout: Int) : Fragment(layout)

interface CoursesFeatureLaunch {
    fun launch(): FragmentScreen
}

interface CoursesFeatureContent {
    val tag: String
    fun content(feature: () -> CoursesFeature): BaseCoursesFragment
}

interface CoursesFeatureScreen : CoursesFeatureContent, CoursesFeatureLaunch