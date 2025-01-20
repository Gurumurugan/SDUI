package com.sdui.ui.theme

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

fun textFont(style: String): FontWeight {
    return when (style) {
        "bold" -> FontWeight.Bold
        "thin" -> FontWeight.Thin
        else -> FontWeight.Normal // Provide a default or fallback
    }
}
fun getAlignment(alignment: String): Alignment {
    return when (alignment) {
        "center" -> Alignment.Center
        "topStart" -> Alignment.TopStart
        "topEnd" -> Alignment.TopEnd
        "bottomStart" -> Alignment.BottomStart
        "bottomEnd" -> Alignment.BottomEnd
        "centerStart" -> Alignment.CenterStart
        "centerEnd" -> Alignment.CenterEnd
        else -> Alignment.Center // Default alignment
    }
}
