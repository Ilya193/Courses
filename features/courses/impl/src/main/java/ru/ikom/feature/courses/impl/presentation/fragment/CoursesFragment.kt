package ru.ikom.feature.courses.impl.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature.courses.api.BaseCoursesFragment
import ru.ikom.feature.courses.api.CoursesFeature
import ru.ikom.feature.courses.api.CoursesFeatureScreen
import ru.ikom.feature.courses.impl.R
import ru.ikom.feature.courses.impl.presentation.component.CoursesComponent
import ru.ikom.feature.courses.impl.presentation.component.CoursesDependencies
import ru.ikom.feature.courses.impl.presentation.view.CoursesView
import ru.ikom.navigation.defaultFragmentScreen

fun defaultCoursesFeatureScreen(
    deps: () -> CoursesDependencies
) =
    object : CoursesFeatureScreen {
        override val tag: String get() = CoursesFragment::class.java.simpleName

        override fun launch(): FragmentScreen =
            defaultFragmentScreen {
                it.get(CoursesFragment::class.java)
            }

        override fun content(feature: () -> CoursesFeature): BaseCoursesFragment =
            CoursesFragment(feature, deps())
    }

class CoursesFragment(
    private val feature: () -> CoursesFeature,
    private val deps: CoursesDependencies,
) : BaseCoursesFragment(R.layout.courses_content) {

    private val component: CoursesComponent by viewModels {
        CoursesComponent.Factory(feature, deps)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoursesView(
            root = view,
            component = component,
            lifecycleScope = viewLifecycleOwner.lifecycleScope,
        )
    }
}