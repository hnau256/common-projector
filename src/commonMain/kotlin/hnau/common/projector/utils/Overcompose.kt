package hnau.common.projector.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach

@Composable
fun Overcompose(
    modifier: Modifier = Modifier,
    top: @Composable (() -> Unit)? = null,
    bottom: @Composable (() -> Unit)? = null,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier,
    ) { constraints ->

        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight

        val topConstraints = constraints.copy(
            minWidth = maxWidth,
            minHeight = 0,
        )

        val topPlaceables = top?.let { topNotNull ->
            subcompose(
                slotId = "top",
                content = topNotNull,
            )
                .map { topMeasurable ->
                    topMeasurable.measure(topConstraints)
                }
        } ?: emptyList()

        val topHeight = topPlaceables
            .maxOfOrNull(Placeable::measuredHeight)
            ?: 0

        val bottomPlaceables = bottom?.let { bottomNotNull ->
            val bottomConstraints = topConstraints.offset(vertical = -topHeight)
            subcompose(
                slotId = "bottom",
                content = bottomNotNull,
            )
                .map { bottomMeasurable ->
                    bottomMeasurable.measure(bottomConstraints)
                }
        } ?: emptyList()

        val bottomHeight = bottomPlaceables
            .maxOfOrNull(Placeable::measuredHeight)
            ?: 0

        val contentConstraints = topConstraints.copy(
            minHeight = maxHeight,
        )
        val contentPlaceables = subcompose(
            slotId = "content",
        ) {
            content(
                PaddingValues(
                    top = topHeight.toDp(),
                    bottom = bottomHeight.toDp(),
                )
            )
        }
            .map { contentMeasurable ->
                contentMeasurable.measure(contentConstraints)
            }

        layout(
            width = maxWidth,
            height = maxHeight,
        ) {

            contentPlaceables.fastForEach { contentPlaceable ->
                contentPlaceable.placeRelative(
                    x = 0,
                    y = 0,
                )
            }

            bottomPlaceables.fastForEach { bottomPlaceable ->
                bottomPlaceable.placeRelative(
                    x = 0,
                    y = maxHeight - bottomPlaceable.measuredHeight,
                )
            }

            topPlaceables.fastForEach { topPlaceable ->
                topPlaceable.placeRelative(
                    x = 0,
                    y = 0,
                )
            }
        }
    }
}