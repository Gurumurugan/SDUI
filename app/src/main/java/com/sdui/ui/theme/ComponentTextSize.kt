package com.sdui.ui.theme

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp


fun TextSizeHeight(size: String) : Modifier  {
    val sizeValue = size.toIntOrNull() ?: 0
   return Modifier.height(sizeValue.dp)
}


fun TextSizeWidth(size: String) : Modifier  {
    val sizeValue = size.toIntOrNull() ?: 0
    return Modifier.width(sizeValue.dp)
}



fun TextPaddingStart(size: String) : Modifier  {
    val sizeValue = size.toIntOrNull() ?: 0
    return Modifier.padding(start = sizeValue.dp)
}

fun TextFontSize(size: String): TextUnit {
    val sizeValue = size.toIntOrNull() ?: 0f
    return TextUnit(sizeValue.toFloat(), type = TextUnitType.Sp)
}

fun TextSize(size: String) : Dp  {
    val sizeValue = size.toIntOrNull() ?: 0
    return sizeValue.dp
}