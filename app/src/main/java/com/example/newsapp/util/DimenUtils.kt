package com.example.newsapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Number.w: Dp
    @Composable
    get() = (this.toDouble() / 100 * LocalConfiguration.current.screenWidthDp).dp

val Number.h: Dp
    @Composable
    get() = (this.toDouble() / 100 * LocalConfiguration.current.screenHeightDp).dp
