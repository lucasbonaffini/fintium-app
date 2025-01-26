package com.lucashomelab.fintium.common.utils

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.util.*

fun Double.formatCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(this)
}

@SuppressLint("DefaultLocale")
fun Double.formatPercentage(): String {
    return String.format("%.2f%%", this)
}
