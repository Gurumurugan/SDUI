package com.sdui.ui.theme

import androidx.compose.ui.text.font.FontWeight

fun TextFont(style: String): FontWeight {
    return when (style) {
        "bold" -> FontWeight.Bold
        "thin" -> FontWeight.Thin
        else -> FontWeight.Normal // Provide a default or fallback
    }
}
