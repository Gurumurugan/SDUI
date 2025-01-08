package com.sdui.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

fun colorSet(color: String) : Color {
   return when(color){
        "white" -> Color.White
        "black" -> Color.Black
        "red" -> Color.Red
        "blue" -> Color.Blue
        "gray" -> Color.LightGray
        "app_color" -> Color(0xFFDC0049)
       else -> Color.Black // Provide a default or fallback
    }
}