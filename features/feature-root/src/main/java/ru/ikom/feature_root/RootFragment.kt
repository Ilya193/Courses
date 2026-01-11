package ru.ikom.feature_root

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.ikom.feature.auth.api.AuthFeatureScreen
import ru.ikom.feature.feature_root.R
import ru.ikom.feature.main_menu.api.MainMenuFeatureScreen
import ru.ikom.feature_root.component.RootComponent
import ru.ikom.feature_root.component.RootFeature
import ru.ikom.feature_root.component.RootFeatureDependencies
import ru.ikom.navigation.AnimateScreen
import ru.ikom.navigation.BaseFragmentFactory
import ru.ikom.navigation.defaultFragmentScreen

abstract class BaseRootFragment(layout: Int) : Fragment(layout)

interface RootFeatureLaunch {
    fun launch(): FragmentScreen
}

interface RootFeatureContent {
    val tag: String
    fun content(feature: () -> RootFeature): BaseRootFragment
}

interface RootFeatureScreen : RootFeatureContent, RootFeatureLaunch

fun defaultRootFeatureScreen(
    deps: () -> RootFeatureDependencies
) =
    object : RootFeatureScreen {
        override val tag: String get() = RootFragment::class.java.simpleName

        override fun launch(): FragmentScreen =
            defaultFragmentScreen {
                it.get(RootFragment::class.java)
            }

        override fun content(feature: () -> RootFeature): BaseRootFragment =
            RootFragment(feature, deps())
    }

class RootFragment(
    private val feature: () -> RootFeature,
    private val dependencies: RootFeatureDependencies,
    private val authFeatureScreen: AuthFeatureScreen = dependencies.authFeatureScreen,
    private val mainMenuFeatureScreen: MainMenuFeatureScreen = dependencies.mainMenuFeatureScreen,
) : BaseRootFragment(R.layout.root_fragment) {

    private val component: RootComponent by viewModels {
        RootComponent.Factory(feature, dependencies)
    }

    private val fragmentFactory = FragmentFactoryImpl()

    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        object : AppNavigator(
            activity = requireActivity(),
            containerId = R.id.root_content,
            fragmentManager = childFragmentManager,
            fragmentFactory = fragmentFactory
        ) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                if (screen is AnimateScreen) {
                    fragmentTransaction.setCustomAnimations(
                        screen.enter,
                        screen.exit,
                        screen.popEnter,
                        screen.popExit
                    )
                }
            }
        }
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
                authFeatureScreen.tag -> authFragment() as T
                mainMenuFeatureScreen.tag -> mainMenuFragment() as T
                else -> throw NotImplementedError("not impl $clasz")
            }

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            get(loadFragmentClass(classLoader, className))

        fun authFragment() =
            authFeatureScreen.content(feature = component::authFeature)

        fun mainMenuFragment() =
            mainMenuFeatureScreen.content(feature = component::mainMenuFeature)
    }
}