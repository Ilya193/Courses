package ru.ikom.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import ru.ikom.feature_root.RootFeatureLaunch
import ru.ikom.feature_root.component.RootFeature

class AppComponent(
    private val rootFeatureLaunch: RootFeatureLaunch
) : ViewModel() {

    private val cicerone = Cicerone.create()
    private val router = cicerone.router
    val navigationHolder = cicerone.getNavigatorHolder()

    fun initNavigation(isFirst: Boolean) {
        if (!isFirst) return

        router.newRootScreen(rootFeatureLaunch.launch())
    }

    fun rootFeature() =
        object : RootFeature {

        }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val rootFeatureLaunch: RootFeatureLaunch
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AppComponent(rootFeatureLaunch) as T
        }
    }
}