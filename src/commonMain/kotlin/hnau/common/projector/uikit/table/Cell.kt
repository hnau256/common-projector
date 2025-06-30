package hnau.common.projector.uikit.table

import androidx.compose.runtime.Composable

data class Cell(
    val content: @Composable CellScope.() -> Unit,
)