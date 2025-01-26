package com.lucashomelab.fintium.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    size: Int = 40
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

// Optional variant that allows customizing the container
@Composable
fun LoadingSpinnerInBox(
    modifier: Modifier = Modifier,
    spinnerSize: Int = 40,
    alignment: Alignment = Alignment.Center
) {
    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(spinnerSize.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}
