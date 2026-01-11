package ru.ikom.ui.compat

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.IdRes

open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable) {

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }
}

fun <T : Any> T?.requireNotNull(): T = requireNotNull(this)

fun <T : View> View.getViewById(@IdRes id: Int): T = findViewById<T>(id).requireNotNull()