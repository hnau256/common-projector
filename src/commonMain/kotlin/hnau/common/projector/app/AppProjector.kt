package hnau.common.projector.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import arrow.core.Either
import hnau.common.kotlin.Loadable
import hnau.common.kotlin.coroutines.mapWithScope
import hnau.common.kotlin.ifNull
import hnau.common.kotlin.ifTrue
import hnau.common.kotlin.map
import hnau.common.kotlin.valueOrElse
import hnau.common.model.ThemeBrightness
import hnau.common.model.app.AppContext
import hnau.common.model.app.AppModel
import hnau.common.model.goback.GlobalGoBackHandler
import hnau.common.model.goback.GlobalGoBackHandlerImpl
import hnau.common.projector.utils.DynamicColorsGenerator
import hnau.common.projector.utils.buildPrettyColorScheme
import hnau.common.projector.utils.provideDynamicColorsGenerator
import hnau.common.projector.utils.system
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

class AppProjector<M, S, P>(
    scope: CoroutineScope,
    model: AppModel<M, S>,
    createProjector: (CoroutineScope, M, GlobalGoBackHandler) -> P,
    private val content: @Composable (P) -> Unit,
) {

    private data class State<P>(
        val appContext: AppContext,
        val projector: P,
    )

    private val projector: StateFlow<Loadable<State<P>>> = model
        .state
        .mapWithScope(scope) { modelScope, stateOrLoading ->
            stateOrLoading.map { state ->
                val projector = createProjector(
                    modelScope,
                    state.model,
                    GlobalGoBackHandlerImpl(model.goBackHandler),
                )
                State(
                    appContext = state.context,
                    projector = projector,
                )
            }
        }

    @Composable
    fun Content() {
        val state = projector
            .collectAsState()
            .value
            .valueOrElse { return }
        Content(
            appContext = state.appContext,
            projector = state.projector,
        )
    }

    private val dynamicColorsGenerator: DynamicColorsGenerator? =
        provideDynamicColorsGenerator()

    @Composable
    private fun Content(
        appContext: AppContext,
        projector: P,
    ) {

        val brightness: ThemeBrightness = appContext
            .brightness
            .value
            .collectAsState()
            .value
            ?: ThemeBrightness.system

        val colorScheme = appContext
            .tryUseSystemHue
            .value
            .collectAsState()
            .value
            .ifTrue { dynamicColorsGenerator }
            ?.generateDynamicColors(brightness)
            .ifNull {
                val hue = appContext
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