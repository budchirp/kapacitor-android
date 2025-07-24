package com.cankolay.kapacitor.android.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt

fun Color.Companion.fromHex(hexColor: String): Color {
    val color = hexColor.replace(oldValue = "#", newValue = "")
    return Color(
        "#${
            when (color.length) {
                6 -> "FF$color"
                8 -> color
                else -> throw IllegalArgumentException("Invalid hex color: $hexColor")
            }
        }".toColorInt()
    )
}

fun Color.Companion.fromColor(color: Color): String =
    String.format(format = "#%08X", color.toArgb())