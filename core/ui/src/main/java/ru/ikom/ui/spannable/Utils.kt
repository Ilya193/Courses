package ru.ikom.ui.spannable

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan

fun SpannableStringBuilder.appendWithStyle(data: String, color: Int, start: Int) {
    append(data)
    setSpan(
        ForegroundColorSpan(color),
        start,
        length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}