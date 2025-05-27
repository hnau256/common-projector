package hnau.common.projector.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import hnau.common.model.ThemeBrightness

class DynamicColorsGenerator private constructor() {

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun generateDynamicColors(
        brightness: ThemeBrightness,
    ): ColorScheme {
        val context = LocalContext.current
        return when (brightness) {
            ThemeBrightness.Dark -> dynamicDarkColorScheme(context)
            ThemeBrightness.Light -> dynamicLightColorScheme(context)
        }
    }

    companion object {

        fun createIfSupported(): DynamicColorsGenerator? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                DynamicColorsGenerator()
            } else {
                null
            }
    }
}