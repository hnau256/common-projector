package hnau.common.projector.uikit.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> CellScope.Subtable(
    items: ImmutableList<T>,
    modifier: Modifier = Modifier,
    item: @Composable CellScope.(T) -> Unit,
) {
    Table(
        modifier = modifier,
        orientation = orientation.opposite,
        items = items,
        item = item,
        corners = corners,
    )
}

@Composable
fun CellScope.CellBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.(Shape) -> Unit,
) {
    val shape = shape
    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = shape,
        ),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {
        content(shape)
    }
}