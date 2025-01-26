package com.lucashomelab.fintium.domain.model

import androidx.compose.ui.graphics.Color

data class Expense(
    val id: String,
    val title: String,
    val amount: Double,
    val category: BudgetCategory,  // Referencia directa a BudgetCategory
    val date: Long,
    val note: String? = null
) {
    val categoryName: String
        get() = category.name

    val categoryColor: Color
        get() = category.color

    // Podemos agregar más propiedades computed si las necesitamos
}
