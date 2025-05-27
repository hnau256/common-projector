package hnau.common.projector.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import hnau.common.model.ThemeBrightness

val ThemeBrightness.Companion.system: ThemeBrightness
    @Composable
    get() = when (isSystemInDarkTheme()) {
        true -> ThemeBrightness.Dark
        false -> ThemeBrightness.Light
    }