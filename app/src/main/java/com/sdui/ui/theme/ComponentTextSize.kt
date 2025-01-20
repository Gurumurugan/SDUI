package com.sdui.ui.theme

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp


fun textSizeHeight(size: Int) : Modifier  {
   return Modifier.height(size.dp)
}


fun textSizeWidth(size: Int) : Modifier  {
    return Modifier.width(size.dp)
}


fun textPaddingStart(size: Int) : Modifier  {
    return Modifier.padding(start = size.dp)
}

fun textFontSize(size: Int): TextUnit {
    return TextUnit(size.toFloat(), type = TextUnitType.Sp)
}

fun textSize(size: Int) : Dp  {
    return size.dp
}

fun modifierSize(size: Int,padding: Int) : Modifier {
    return Modifier.size(size.dp).padding(padding.dp)
}