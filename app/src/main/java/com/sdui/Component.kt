package com.sdui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sdui.ui.theme.colorSet
import com.sdui.ui.theme.textFontSize
import com.sdui.ui.theme.textSize
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun  GenericColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    jsonArray: JSONArray,
    itemContent: @Composable (JSONObject) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        val jsonObjectList = List(jsonArray.length()) { jsonArray.getJSONObject(it) }
        jsonObjectList.forEach { jsonObject ->
            itemContent(jsonObject)
        }

    }
}



@Composable
fun GenericRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    jsonArray: JSONArray,
    itemContent: @Composable (JSONObject) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        val jsonObjectList = List(jsonArray.length()) { jsonArray.getJSONObject(it) }
        jsonObjectList.forEach { jsonObject ->
            itemContent(jsonObject)
        }
    }
}


@Composable
fun GenericBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    jsonArray: JSONArray,
    itemContent: @Composable (JSONObject) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ){
        val jsonObjectList = List(jsonArray.length()) { jsonArray.getJSONObject(it) }
        jsonObjectList.forEach { jsonObject ->
            itemContent(jsonObject)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpInputComponent(otpLength: Int,
                      rowSpaceBetween: Int,
                      viewPadding: Int,
                      viewWidth: Int,
                      viewHeight: Int,
                      roundedCornerShapeSize : Int,
                      onOtpComplete: (String) -> Unit) {

    val otpValues = remember { mutableStateListOf(*Array(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(textSize(rowSpaceBetween)),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        (0 until otpLength).forEach { index ->
            TextField(
                value = otpValues[index],
                onValueChange = { newValue ->
                    // Allow only a single character
                    if (newValue.length <= 1) {
                        otpValues[index] = newValue
                        if (newValue.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                        if (otpValues.joinToString("").length == otpLength) {
                            onOtpComplete(otpValues.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .padding(textSize(viewPadding))
                    .width(textSize(viewWidth))
                    .height(textSize(viewHeight))
                    .focusRequester(focusRequesters[index]),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(textSize(roundedCornerShapeSize)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Black // Ensures text color is visible
                ),
                keyboardActions = KeyboardActions(onDone = { })
            )
        }
    }
    // Automatically focus the first field on UI load
    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}



@Composable
fun GenericImage(
    imageModel: Any,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1.0f,
    contentDescription: String? = null
) {
    when (imageModel) {
        is Int -> Image(
            painter = painterResource(imageModel),
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            contentDescription = contentDescription
        )
        is String -> Image(
            painter =  rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
                .data(imageModel)
                .decoderFactory(SvgDecoder.Factory())
                .build()),
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            contentDescription = contentDescription
        )
        is Uri -> Image(
            painter =  rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
                    .data(imageModel)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()),
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            contentDescription = contentDescription
        )
        else -> Box {}
    }
}

@Composable
fun GenericSpacer(
    size: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .size(size)
    )
}


@Composable
fun GenericText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    roundedCornerShapeSize : Int,
    buttonWidth: Int? = null,
    buttonHeight: Int? = null,
    colorCode:String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        maxLines = maxLines,
        shape = RoundedCornerShape(textSize(roundedCornerShapeSize)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorSet(colorCode),
            unfocusedBorderColor = colorSet(colorCode),
            disabledBorderColor = colorSet(colorCode),
            errorBorderColor = colorSet(colorCode),
            focusedLabelColor = colorSet(colorCode), // Change label text color when focused
            unfocusedLabelColor = colorSet(colorCode) // Change label text color when unfocused
        )
    )
}

@Composable
fun GenericButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    roundedCornerShapeSize : Int,
    contentPadding:Int,
    colors: ButtonColors = buttonColors(Color.Gray),
    textColor: String,
    fontSize: Int,
    buttonWidth: Int,
    buttonHeight: Int,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(textSize(roundedCornerShapeSize)),
        contentPadding = PaddingValues(textSize(contentPadding)),
        colors = colors,
        modifier = modifier
            .requiredWidth(textSize(buttonWidth))
            .height(textSize(buttonHeight)),
    ) {
        Text(text = text, color = colorSet(textColor), fontSize = textFontSize(fontSize))
    }
}


