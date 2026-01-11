package ru.ikom.ui.diff

interface Value<T: Any> {
    var value: T?

    companion object {
        fun <T: Any> defaultValue(initial: T? = null): Value<T> = object : Value<T> {
            override var value: T? = initial
        }
    }
}

inline fun <T: Any> Value<T>.onChange(newValue: T?, block: () -> Unit) {
    if (value != newValue) {
        value = newValue
        block()
    }
}

fun <T: Any> Value<T>.onChange(newValue: T?): Boolean {
    if (value != newValue) {
        value = newValue
        return true
    }
    return false
}

inline fun <T: Any> Value<T>.onChangeReference(newValue: T?, block: () -> Unit) {
    if (value !== newValue) {
        value = newValue
        block()
    }
}

fun <T: Any> Value<T>.onChangeReference(newValue: T?): Boolean {
    if (value !== newValue) {
        value = newValue
        return true
    }
    return false
}