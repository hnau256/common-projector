package hnau.common.projector.uikit.table

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import hnau.common.projector.uikit.shape.HnauShape
import hnau.common.projector.uikit.shape.create
import hnau.common.projector.uikit.table.layout.LayoutWeightElement

data class CellScope(
    val orientation: TableOrientation,
    val corners: TableCorners,
) {

    fun Modifier.weight(
        weight: Float,
    ): Modifier = then(
        LayoutWeightElement(
            weight = weight,
        )
    )

    val shape: Shape
        @Composable
        get() = HnauShape.create(
            startTopRoundCorners = corners.startTopIsOpened,
            startBottomRoundCorners = corners.startBottomIsOpened,
            endTopRoundCorners = corners.endTopIsOpened,
            endBottomRoundCorners = corners.endBottomIsOpened,
        )
}