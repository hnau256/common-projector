package hnau.common.projector.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import hnau.common.kotlin.ifNull
import hnau.common.kotlin.ifTrue
import hnau.common.model.ThemeBrightness
import hnau.common.model.app.AppModel
import hnau.common.model.goback.GlobalGoBackHandler
import hnau.common.model.goback.GlobalGoBackHandlerImpl
import hnau.common.projector.utils.DynamicColorsGenerator
import hnau.common.projector.utils.buildPrettyColorScheme
import hnau.common.projector.utils.provideDynamicColorsGenerator
import hnau.common.projector.utils.system
import kotlinx.coroutines.CoroutineScope

class AppProjector<M, S, P>(
    scope: CoroutineScope,
    private val model: AppModel<M, S>,
    createProjector: (CoroutineScope, M, GlobalGoBackHandler) -> P,
    private val content: @Composable (P) -> Unit,
) {

    private val projector = createProjector(
        scope,
        model.model,
        GlobalGoBackHandlerImpl(model.goBackHandler),
    )

    private val dynamicColorsGenerator: DynamicColorsGenerator? =
        provideDynamicColorsGenerator()

    @Composable
    fun Content() {

        val brightness: ThemeBrightness = model
            .appContext
            .brightness
            .value
            .collectAsState()
            .value
            ?: ThemeBrightness.system

        val colorScheme = model
            .appContext
            .tryUseSystemHue
            .value
            .collectAsState()
            .value
            .ifTrue { dynamicColorsGenerator }
            ?.generateDynamicColors(brightness)
            .ifNull {
                val hue = model
                    .appContext
                    .fallbackHue
                    .value
                    .collectAsState()
                    .value
                buildPrettyColorScheme(
                    primaryHue = hue,
                    brightness = brightness,
                )
            }

        MaterialTheme(
            colorScheme = colorScheme,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onBackground,
                ) {
                    content(projector)
                }
            }
        }
    }
}