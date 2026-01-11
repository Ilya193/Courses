package ru.ikom.feature.auth.impl.presentation.view

import android.text.Editable
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.ikom.feature.auth.impl.R
import ru.ikom.feature.auth.impl.presentation.component.AuthComponent
import ru.ikom.ui.compat.SimpleTextWatcher
import ru.ikom.ui.compat.getViewById
import ru.ikom.ui.spannable.appendWithStyle

class AuthView(
    private val root: View,
    private val component: AuthComponent,
    private val lifecycleScope: CoroutineScope,
) {

    private val binding = Binding(root)

    private val emailTextWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            val data = binding.emailInput.input.text.toString()
            component.onInputEmail(data)
        }
    }

    private val passwordTextWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            val data = binding.passwordInput.input.text.toString()
            component.onInputPassword(data)
        }
    }

    init {
        initEmailInput()
        initPasswordInput()
        initNoAccount()

        binding.login.setOnClickListener { component.onClickLogin() }
        binding.containerVk.setOnClickListener { component.onClickVk() }
        binding.containerOk.setOnClickListener { component.onClickOk() }

        lifecycleScope.launch {
            component.state.collect {
                updateState(it)
            }
        }
    }

    private fun initEmailInput() = with(binding.emailInput) {
        val emailResource = ContextCompat.getString(root.context, ru.ikom.ui.R.string.email)
        val hintEmailResource = ContextCompat.getString(root.context, ru.ikom.ui.R.string.hint_example_email)
        title.text = emailResource
        input.hint = hintEmailResource
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        input.imeOptions = EditorInfo.IME_ACTION_NEXT
        input.addTextChangedListener(emailTextWatcher)
    }

    private fun initPasswordInput() = with(binding.passwordInput) {
        val passwordResource = ContextCompat.getString(root.context, ru.ikom.ui.R.string.password)
        val hintPasswordResource = ContextCompat.getString(root.context, ru.ikom.ui.R.string.enter_password)
        title.text = passwordResource
        input.hint = hintPasswordResource
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        input.imeOptions = EditorInfo.IME_ACTION_DONE
        input.addTextChangedListener(passwordTextWatcher)
    }

    private fun initNoAccount() = with(binding) {
        val defaultWhiteColor = ContextCompat.getColor(root.context, ru.ikom.ui.R.color.default_white)
        val greenColor = ContextCompat.getColor(root.context, ru.ikom.ui.R.color.green)
        val noAccountResource = ContextCompat.getString(root.context, ru.ikom.ui.R.string.no_account)
        val registrationResource = ContextCompat.getString(root.context, ru.ikom.ui.R.string.registration)
        val noAccountSpannable = buildSpannedString {
            appendWithStyle(noAccountResource, defaultWhiteColor, 0)
            appendWithStyle(" $registrationResource", greenColor, length)
        }
        noAccount.text = noAccountSpannable
    }

    private fun updateState(state: AuthComponent.State) {
        updateLogin(state.loginIsAvailable)
    }

    private fun updateLogin(state: Boolean) {
        binding.login.isEnabled = state
    }
}

private class Binding(root: View) {

    val authContent = root.getViewById<LinearLayout>(R.id.auth_content)
    val noAccount = authContent.getViewById<TextView>(R.id.no_account)

    val emailInput = AuthInputBinding(root.getViewById(R.id.input_email))
    val passwordInput = AuthInputBinding(root.getViewById(R.id.input_password))

    val login = root.getViewById<TextView>(R.id.login)

    val authOtherMethodsContainer = root.getViewById<LinearLayout>(R.id.auth_other_methods)
    val containerVk = authOtherMethodsContainer.getViewById<FrameLayout>(R.id.container_vk)
    val containerOk = authOtherMethodsContainer.getViewById<FrameLayout>(R.id.container_ok)
}

private class AuthInputBinding(root: View) {

    val title = root.getViewById<TextView>(R.id.title_input)
    val input = root.getViewById<EditText>(R.id.input)
}