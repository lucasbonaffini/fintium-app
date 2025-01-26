package com.lucashomelab.fintium.presentation.charts.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

@Composable
fun IncomeDonutChart(
    monthlyIncome: Double,
    businessIncome: Double,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                setHoleRadius(70f)
                setTransparentCircleRadius(75f)
                setDrawEntryLabels(false)
                legend.isEnabled = true
                legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                setUsePercentValues(true)
                animateY(1000, Easing.EaseInOutQuad)
            }
        },
        update = { chart ->
            val entries = listOf(
                PieEntry(monthlyIncome.toFloat(), "Monthly salary"),
                PieEntry(businessIncome.toFloat(), "Business Project")
            )

            val dataSet = PieDataSet(entries, "Income Sources").apply {
                colors = listOf(
                    Color(0xFF9C27B0).toArgb(),
                    Color(0xFFFF4B8C).toArgb()
                )
                setDrawValues(true)
                valueTextSize = 14f
                valueFormatter = PercentFormatter()
            }

            chart.data = PieData(dataSet)
            chart.invalidate()
        }
    )
}
