package com.lucashomelab.fintium.presentation.charts.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.lucashomelab.fintium.domain.model.Expense
import com.lucashomelab.fintium.presentation.charts.CurrencyFormatter
import com.lucashomelab.fintium.presentation.charts.DateAxisValueFormatter
import com.lucashomelab.fintium.presentation.reports.ReportsViewModel

@Composable
fun ExpensesLineChart(
    data: List<Expense>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                setTouchEnabled(true)
                setDrawGridBackground(false)
                legend.isEnabled = false

                xAxis.apply {
                    setDrawGridLines(false)
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = DateAxisValueFormatter()
                }

                axisLeft.apply {
                    setDrawGridLines(true)
                    setDrawAxisLine(false)
                    valueFormatter = CurrencyFormatter()
                }

                axisRight.isEnabled = false

                animateX(1000, Easing.EaseInOutQuart)
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, point ->
                Entry(index.toFloat(), point.amount.toFloat())
            }

            val dataSet = LineDataSet(entries, "Expenses").apply {
                color = Color(0xFFFF4B8C).toArgb()
                setDrawFilled(true)
                fillColor = Color(0xFFFF4B8C).copy(alpha = 0.3f).toArgb()
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawCircles(false)
                setDrawValues(false)
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}

