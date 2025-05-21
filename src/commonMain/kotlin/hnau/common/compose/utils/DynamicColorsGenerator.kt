package hnau.common.compose.utils

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

fun interface DynamicColorsGenerator {

    @Composable
    fun generateDynamicColors(
        brightness: ThemeBrightness,
    ): ColorScheme

    companion object
}