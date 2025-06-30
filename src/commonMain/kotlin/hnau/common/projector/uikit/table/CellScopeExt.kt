package hnau.common.projector.uikit.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.StateFlow

fun Subtable(
    modifier: Modifier = Modifier,
    cells: ImmutableList<Cell>,
): Cell = Cell {
    Table(
        modifier = modifier,
        orientation = orientation.opposite,
        corners = corners,
        cells = cells,
    )
}

fun Subtable(
    modifier: Modifier = Modifier,
    cells: StateFlow<ImmutableList<Cell>>,
): Cell = Cell {
    Table(
        modifier = modifier,
        orientation = orientation.opposite,
        corners = corners,
        cells = cells.collectAsState().value,
    )
}

fun CellBox(
    modifier: Modifier = Modifier,
    weight: Float? = null,
    backgroundColor: @Composable () -> Color = { MaterialTheme.colorScheme.surfaceContainerLow },
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.(Shape) -> Unit,
): Cell = Cell {
    val shape = shape
    Box(
        modifier = modifier
            .then(
                weight
                    ?.let { weight -> Modifier.weight(weight) }
                    ?: Modifier
            )
            .background(
                color = backgroundColor(),
                shape = shape,
            ),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {
        content(shape)
    }
}