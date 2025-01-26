package com.lucashomelab.fintium.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lucashomelab.fintium.ui.theme.PositiveGreen
import com.lucashomelab.fintium.ui.theme.NegativeRed
import java.text.NumberFormat
import java.util.*

@Composable
fun PortfolioSummaryCard(
    totalValue: Double,
    totalProfitLoss: Double,
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Total Portfolio Value",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = numberFormat.format(totalValue),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val profitLossColor = if (totalProfitLoss >= 0) PositiveGreen else NegativeRed
                val prefix = if (totalProfitLoss >= 0) "+" else ""

                Text(
                    text = "Total P/L: ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$prefix${numberFormat.format(totalProfitLoss)}",
                    color = profitLossColor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
