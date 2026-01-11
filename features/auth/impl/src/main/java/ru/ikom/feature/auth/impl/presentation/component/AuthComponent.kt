package ru.ikom.feature.auth.impl.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ikom.feature.auth.api.AuthFeature
import ru.ikom.ui.components.BaseRootComponent

class AuthComponent(
    private val feature: AuthFeature,
) : BaseRootComponent<AuthComponent.State, AuthComponent.Msg, AuthComponent.Label>(initialState()) {

    fun onInputEmail(data: String) {
        val state = uiState.value

        val loginIsAvailable = loginIsAvailable(data, state.password)

        dispatch {
            reduce(Msg.UpdateEmail(data))
                .reduce(Msg.UpdateLoginIsAvailable(loginIsAvailable))
        }
    }

    fun onInputPassword(data: String) {
        val state = uiState.value

        val loginIsAvailable = loginIsAvailable(state.email, data)

        dispatch {
            reduce(Msg.UpdatePassword(data))
                .reduce(Msg.UpdateLoginIsAvailable(loginIsAvailable))
        }
    }

    fun onClickLogin() {
        val state = uiState.value

        val loginIsAvailable = loginIsAvailable(state.email, state.password)

        if (!loginIsAvailable) return

        feature.userIsAuthorized()
    }

    fun onClickVk() {
        publish(Label.OnOpenVk())
    }

    fun onClickOk() {
        publish(Label.OnOpenOk())
    }

    private fun loginIsAvailable(email: String, password: String) =
        email.isValidEmail() && password.isNotEmpty()

    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    override fun State.reduce(msg: Msg): State =
        when (msg) {
            is Msg.UpdateEmail -> copy(email = msg.email)
            is Msg.UpdatePassword -> copy(password = msg.password)
            is Msg.UpdateLoginIsAvailable -> copy(loginIsAvailable = msg.isAvailable)
        }

    data class State(
        val email: String,
        val password: String,
        val loginIsAvailable: Boolean,
    )

    sealed interface Msg {
        class UpdateEmail(val email: String) : Msg
        class UpdatePassword(val password: String) : Msg
        class UpdateLoginIsAvailable(val isAvailable: Boolean) : Msg
    }

    sealed interface Label {
        class OnOpenVk : Label
        class OnOpenOk : Label
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val feature: () -> AuthFeature
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthComponent(feature()) as T
        }
    }
}

private fun initialState() =
    AuthComponent.State(
        email = "",
        password = "",
        loginIsAvailable = false,
    )