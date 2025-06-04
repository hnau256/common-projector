package hnau.common.projector.uikit.table

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.util.fastForEachIndexed
import hnau.common.projector.uikit.table.layout.TableLayout
import hnau.common.projector.uikit.utils.Dimens
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> Table(
    orientation: TableOrientation,
    items: ImmutableList<T>,
    modifier: Modifier = Modifier,
    corners: TableCorners = TableCorners.opened,
    item: @Composable CellScope.(T) -> Unit,
) {
    TableLayout(
        orientation = orientation,
        modifier = modifier,
        separationPx = with(LocalDensity.current) { Dimens.chipsSeparation.roundToPx() },
    ) {
        items.fastForEachIndexed { i, item ->
            val closeStartOrTop = i > 0
            val closeEndOrBottom = i < items.lastIndex
            val cellScope = remember(corners, orientation, closeStartOrTop, closeEndOrBottom) {
                CellScope(
                    orientation = orientation,
                    corners = corners.close(
                        orientation = orientation,
                        startOrTop = closeStartOrTop,
                        endOrBottom = closeEndOrBottom,
                    )
                )
            }
            cellScope.item(item)
        }
    }
}