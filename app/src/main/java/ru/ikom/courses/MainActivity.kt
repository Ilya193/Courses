package ru.ikom.courses

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.ikom.courses.app.App
import ru.ikom.courses.di.AppContainer
import ru.ikom.feature_root.RootFeatureScreen
import ru.ikom.navigation.BaseFragmentFactory

class MainActivity : AppCompatActivity() {

    private val appContainer: AppContainer by lazy(LazyThreadSafetyMode.NONE) {
        (application as App).appContainer
    }

    private val rootFeatureScreen: RootFeatureScreen by lazy(LazyThreadSafetyMode.NONE) {
        appContainer.provideRootFeatureScreen()
    }

    private val appComponent: AppComponent by viewModels {
        AppComponent.Factory(rootFeatureScreen)
    }

    private val fragmentFactory = FragmentFactoryImpl()

    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        AppNavigator(
            activity = this,
            containerId = R.id.main_content,
            fragmentManager = supportFragmentManager,
            fragmentFactory = fragmentFactory
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContentView(R.layout.activity_main)

        appComponent.initNavigation(savedInstanceState == null)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        appComponent.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        appComponent.navigationHolder.removeNavigator()
        super.onPause()
    }

    @Suppress("UNCHECKED_CAST")
    private inner class FragmentFactoryImpl : BaseFragmentFactory() {
        override fun <T : Fragment> get(clasz: Class<T>): T =
            when (clasz.simpleName) {
                rootFeatureScreen.tag -> rootFragment() as T
                else -> throw NotImplementedError("not impl $clasz")
            }

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            get(loadFragmentClass(classLoader, className))

        fun rootFragment() =
            rootFeatureScreen.content(feature = appComponent::rootFeature)
    }
}