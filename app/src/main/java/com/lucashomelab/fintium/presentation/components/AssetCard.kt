package com.lucashomelab.fintium.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucashomelab.fintium.domain.model.Asset
import java.text.NumberFormat
import java.util.*

@Composable
fun AssetCard(
    asset: Asset,
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = asset.symbol,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = numberFormat.format(asset.currentPrice),
                    style = MaterialTheme.typography.bodyLarge
                )

                val profitLossColor = if (asset.profitLoss >= 0)
                    Color(0xFF4CAF50) else Color(0xFFE57373)
                val profitLossText =
                    "${if (asset.profitLoss >= 0) "+" else ""}${String.format("%.2f", asset.profitLossPercentage)}%"

                Text(
                    text = profitLossText,
                    color = profitLossColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
