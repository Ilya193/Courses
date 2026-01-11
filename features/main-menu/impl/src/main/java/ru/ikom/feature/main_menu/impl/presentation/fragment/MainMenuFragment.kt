package ru.ikom.feature.main_menu.impl.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature.courses.api.CoursesFeatureContent
import ru.ikom.feature.main_menu.api.BaseMainMenuFragment
import ru.ikom.feature.main_menu.api.MainMenuFeature
import ru.ikom.feature.main_menu.api.MainMenuFeatureScreen
import ru.ikom.feature.main_menu.impl.R
import ru.ikom.feature.main_menu.impl.presentation.component.MainMenuComponent
import ru.ikom.feature.main_menu.impl.presentation.component.MainMenuDependencies
import ru.ikom.navigation.BaseFragmentFactory
import ru.ikom.navigation.defaultAnimationScreen

fun defaultMainMenuFeatureScreen(
    deps: () -> MainMenuDependencies
) =
    object : MainMenuFeatureScreen {
        override val tag: String get() = MainMenuFragment::class.java.simpleName

        override fun launch(): FragmentScreen =
            defaultAnimationScreen(
                enter = ru.ikom.ui.R.anim.slide_in_right,
                exit = ru.ikom.ui.R.anim.slide_out_left,
                popEnter = ru.ikom.ui.R.anim.slide_in_left,
                popExit = ru.ikom.ui.R.anim.slide_out_right,
            ) {
                it.get(MainMenuFragment::class.java)
            }

        override fun content(feature: () -> MainMenuFeature): BaseMainMenuFragment =
            MainMenuFragment(feature, deps())
    }

class MainMenuFragment(
    private val feature: () -> MainMenuFeature,
    private val deps: MainMenuDependencies,
    private val coursesFeatureContent: CoursesFeatureContent = deps.coursesFeatureScreen,
) : BaseMainMenuFragment(R.layout.main_menu_content) {

    private val component: MainMenuComponent by viewModels {
        MainMenuComponent.Factory(feature, deps)
    }

    private val fragmentFactory = FragmentFactoryImpl()

    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        AppNavigator(
            activity = requireActivity(),
            containerId = R.id.main_menu_content,
            fragmentManager = childFragmentManager,
            fragmentFactory = fragmentFactory
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        component.initNavigation(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        component.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        component.navigationHolder.removeNavigator()
        super.onPause()
    }

    @Suppress("UNCHECKED_CAST")
    private inner class FragmentFactoryImpl : BaseFragmentFactory() {
        override fun <T : Fragment> get(clasz: Class<T>): T =
            when (clasz.simpleName) {
                coursesFeatureContent.tag -> coursesFragment() as T
                else -> throw NotImplementedError("not impl $clasz")
            }

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            get(loadFragmentClass(classLoader, className))

        fun coursesFragment() =
            coursesFeatureContent.content(feature = component::coursesFeature)
    }
}