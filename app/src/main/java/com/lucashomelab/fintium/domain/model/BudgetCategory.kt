package com.lucashomelab.fintium.domain.model

import androidx.compose.ui.graphics.Color

data class BudgetCategory(
    val id: String,
    val name: String,
    val icon: String,
    val color: Color,
    val budget: Double,
    val spent: Double,
    val transactions: Int = 0


)

