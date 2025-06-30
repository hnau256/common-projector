package hnau.common.projector.uikit.table

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

internal interface LineScope {

    @Stable
    fun Modifier.weight(
        @FloatRange(from = 0.0, fromInclusive = false) weight: Float,
    ): Modifier

    @Stable
    fun Modifier.fillMaxAcross(): Modifier

}

@Composable
internal fun Line(
    orientation: TableOrientation,
    modifier: Modifier = Modifier,
    separation: Dp,
    content: @Composable LineScope.() -> Unit,
) {
    when (orientation) {
        TableOrientation.Vertical -> Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(separation),
        ) {
            val scope = remember(this) { ColumnLineScope(this) }
            scope.content()
        }

        TableOrientation.Horizontal -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(separation),
        ) {
            val scope = remember(this) { RowLineScope(this) }
            scope.content()
        }
    }
}

private class RowLineScope(
    private val parent: RowScope,
) : LineScope {

    @Stable
    override fun Modifier.weight(
        weight: Float,
    ): Modifier = with(parent) {
        weight(
            weight = weight,
        )
    }

    @Stable
    override fun Modifier.fillMaxAcross(): Modifier = fillMaxHeight()
}

private class ColumnLineScope(
    private val parent: ColumnScope,
) : LineScope {

    @Stable
    override fun Modifier.weight(
        weight: Float,
    ): Modifier = with(parent) {
        weight(
            weight = weight,
        )
    }

    @Stable
    override fun Modifier.fillMaxAcross(): Modifier = fillMaxWidth()
}