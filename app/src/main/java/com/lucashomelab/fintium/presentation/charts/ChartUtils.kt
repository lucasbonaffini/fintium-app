package com.lucashomelab.fintium.presentation.charts

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class DateAxisValueFormatter : ValueFormatter() {
    private val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
    private val calendar = Calendar.getInstance()
    private val baseTimestamp = calendar.timeInMillis

    override fun getFormattedValue(value: Float): String {
        calendar.timeInMillis = baseTimestamp + (value.toLong() * 24 * 60 * 60 * 1000)
        return dateFormat.format(calendar.time)
    }
}

class CurrencyFormatter : ValueFormatter() {
    @SuppressLint("DefaultLocale")
    override fun getFormattedValue(value: Float): String {
        return "$${String.format("%.2f", value)}"
    }
}

class PercentFormatter : ValueFormatter() {
    @SuppressLint("DefaultLocale")
    override fun getFormattedValue(value: Float): String {
        return "${String.format("%.1f", value)}%"
    }
}
