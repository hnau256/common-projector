package hnau.common.projector.utils

import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import hnau.common.model.goback.GoBackHandler

@Composable
fun GoBackHandler.NavigationIcon() {
    val onClick = collectAsState().value ?: return
    IconButton(
        onClick = onClick,
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack)
    }
}