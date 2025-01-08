package com.sdui.ui.theme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest


@Composable
fun ImagePainter(imageTwo: String) : Painter {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageTwo)
            .decoderFactory(SvgDecoder.Factory())
            .build()
    )
    return painter
}

fun ModifierSize(size: String,padding: String) : Modifier {
    val sizeValue = size.toIntOrNull() ?: 0
    val paddingValue = padding.toIntOrNull() ?: 0
    return Modifier.size(sizeValue.dp).padding(paddingValue.dp)
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

fun getTextAlignment(alignment: String): TextAlign {
    return when (alignment) {
        "center" -> TextAlign.Center
        "start" -> TextAlign.Start
        "end" -> TextAlign.End
        "left" -> TextAlign.Left
        "right" -> TextAlign.Right
        "justify" -> TextAlign.Justify
        else -> TextAlign.Unspecified // Default alignment
    }
}
