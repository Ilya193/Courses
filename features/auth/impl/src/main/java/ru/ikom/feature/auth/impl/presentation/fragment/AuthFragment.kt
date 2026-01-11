package ru.ikom.feature.auth.impl.presentation.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.launch
import ru.ikom.feature.auth.api.AuthFeature
import ru.ikom.feature.auth.api.AuthFeatureScreen
import ru.ikom.feature.auth.api.BaseAuthFragment
import ru.ikom.feature.auth.impl.R
import ru.ikom.feature.auth.impl.presentation.component.AuthComponent
import ru.ikom.feature.auth.impl.presentation.view.AuthView
import ru.ikom.navigation.defaultFragmentScreen

fun defaultAuthScreen(

) =
    object : AuthFeatureScreen {
        override val tag: String get() = AuthFragment::class.java.simpleName

        override fun launch(): FragmentScreen =
            defaultFragmentScreen {
                it.get(AuthFragment::class.java)
            }

        override fun content(feature: () -> AuthFeature): BaseAuthFragment =
            AuthFragment(feature)
    }

class AuthFragment(
    private val feature: () -> AuthFeature,
) : BaseAuthFragment(R.layout.auth_content) {

    private val component: AuthComponent by viewModels { AuthComponent.Factory(feature) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AuthView(
            root = view,
            component = component,
            lifecycleScope = viewLifecycleOwner.lifecycleScope,
        )

        viewLifecycleOwner.lifecycleScope.launch {
            component.labels.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).collect {
                when (it) {
                    is AuthComponent.Label.OnOpenVk -> onOpenVk(it)
                    is AuthComponent.Label.OnOpenOk -> onOpenOk(it)
                }
            }
        }
    }

    private fun onOpenVk(label: AuthComponent.Label.OnOpenVk) {
        val link = ContextCompat.getString(requireContext(), ru.ikom.ui.R.string.vk_link)
        onOpenBrowser(link)
    }

    private fun onOpenOk(label: AuthComponent.Label.OnOpenOk) {
        val link = ContextCompat.getString(requireContext(), ru.ikom.ui.R.string.ok_link)
        onOpenBrowser(link)
    }

    private fun onOpenBrowser(link: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) { }
    }
}