package com.sdui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdui.MainActivity.Companion.baseView
import com.sdui.MainActivity.Companion.screenFourChildrenArray
import com.sdui.MainActivity.Companion.screenThreeChildrenArray
import com.sdui.MainActivity.Companion.screenTwoChildrenArray
import com.sdui.ui.theme.SDUITheme
import com.sdui.ui.theme.colorSet
import com.sdui.ui.theme.getAlignment
import com.sdui.ui.theme.getTextAlignment
import com.sdui.ui.theme.modifierSize
import com.sdui.ui.theme.textFont
import com.sdui.ui.theme.textFontSize
import com.sdui.ui.theme.textPaddingStart
import com.sdui.ui.theme.textSize
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : ComponentActivity() {

    companion object {
        var baseView :String? = null
        var screenTwoChildrenArray : JSONArray? = null
        var screenThreeChildrenArray : JSONArray? = null
        var screenFourChildrenArray : JSONArray? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SDUITheme {
                MyApp()
            }
        }
    }
}

@Composable
fun CenteredImage() {
    val painter = rememberAsyncImagePainter(R.raw.splash_screen)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "My Image Description",
            modifier = Modifier.size(250.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SDUITheme {
        CenteredImage()
    }
}


@Composable
fun MyApp() {
    AppNavigation()
}


@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        getJsonData("https://stgwww.luv.com/main_sdui/sdui.json",context)
        val jsonString = readJsonFromRaw(context, R.raw.sdui)
        //getData(jsonString)
        delay(1000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }
    // Splash Screen UI
    CenteredImage()
}
private fun getJsonData(url: String, context: Context) {
    val queue = Volley.newRequestQueue(context)
    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
        Log.d("Response", "response: $response")
        getData(response.toString())
    }, { error ->
        Log.d("Response", "response: $error")
    }
    )
    queue.add(jsonObjectRequest)
}
private fun getData(response: String){
    val rootObject = JSONObject(response)


    val screenTwoArray = rootObject.getJSONArray("screen_two")
    val screenTwoObject = screenTwoArray.getJSONObject(0)
    val screenTwoLayoutArray = screenTwoObject.getJSONArray("screen_two_layout")
    val firstLayoutObject = screenTwoLayoutArray.getJSONObject(0)
    baseView = firstLayoutObject.getString("type")
    val baseViewProperties = firstLayoutObject.getJSONObject("properties")
    screenTwoChildrenArray = firstLayoutObject.getJSONArray("screen_two_children")


    val screenThreeArray = rootObject.getJSONArray("screen_three")
    val screenThreeObject = screenThreeArray.getJSONObject(0)
    val screenThreeLayoutArray = screenThreeObject.getJSONArray("screen_three_layout")
    val twoLayoutObject = screenThreeLayoutArray.getJSONObject(0)
    baseView = twoLayoutObject.getString("type")
    screenThreeChildrenArray = twoLayoutObject.getJSONArray("screen_three_children")


    val screenFourArray = rootObject.getJSONArray("screen_four")
    val screenFourObject = screenFourArray.getJSONObject(0)
    val screenFourLayoutArray = screenFourObject.getJSONArray("screen_four_layout")
    val fourLayoutObject = screenFourLayoutArray.getJSONObject(0)
    baseView = fourLayoutObject.getString("type")
    screenFourChildrenArray = fourLayoutObject.getJSONArray("screen_four_children")

}

@Composable
fun LoginScreen(navController: NavHostController) {
    BaseView(baseView!!,navController,screenTwoChildrenArray!!)
}


@Composable
fun MobileNumberUI(navController: NavHostController) {
    BaseView(baseView!!,navController, screenThreeChildrenArray!!)
}

@Composable
fun OTPUI(navController: NavHostController) {
    BaseView(baseView!!,navController, screenFourChildrenArray!!)
}

private fun readJsonFromRaw(context: Context, rawResourceId: Int): String {
    val inputStream = context.resources.openRawResource(rawResourceId)
    return inputStream.bufferedReader().use { it.readText() }
}

@Composable
fun BaseView(type:String,navController: NavHostController,jsonArray: JSONArray){
    when(BaseViewType.valueOf(type)){
        BaseViewType.Column -> {
            GenericColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                jsonArray  = jsonArray
            ) { item ->
                ComponentView(type = item.getString("type"),properties = item.getJSONObject("properties"),navController)
            }
        }
        BaseViewType.Row -> {
            GenericRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                jsonArray  = screenTwoChildrenArray!!
            ) { item ->
                ComponentView(type = item.getString("type"),properties = item.getJSONObject("properties"),navController)
            }
        }
        BaseViewType.Box ->{
            GenericBox(
                modifier = Modifier.fillMaxSize(),
                jsonArray  = screenThreeChildrenArray!!
            ) { item ->
                ComponentView(type = item.getString("type"),properties = item.getJSONObject("properties"),navController)
            }
        }

    }
}

@Composable
fun ComponentView(type: String,properties: JSONObject,navController: NavHostController) {
    when (ComponentViewType.valueOf(type)) {
        ComponentViewType.Image -> {
            GenericImage(
                imageModel = properties.getString("image"),
                modifier = modifierSize(properties.getInt("height"),properties.getInt("padding")),
                alignment = getAlignment(properties.getString("alignment")),
                contentScale = ContentScale.Crop
            )
        }
        ComponentViewType.Text -> {
                GenericText(
                    text = properties.getString("text"),
                    textAlign = getTextAlignment(properties.getString("textAlign")),
                    modifier = textPaddingStart(properties.getInt("padding")),
                    color = colorSet(properties.getString("color")),
                    fontSize = textFontSize(properties.getInt("fontSize")),
                    fontWeight = textFont(properties.getString("fontWeight"))
                )
        }
        ComponentViewType.Button -> {
            GenericButton(
                onClick = { navController.navigate(properties.getString("navigate")) },
                text = properties.getString("text"),
                modifier = textPaddingStart(properties.getInt("padding")),
                colors = buttonColors(colorResource(id = R.color.app)),
                buttonHeight = properties.getInt("height"),
                buttonWidth = properties.getInt("width"),
                fontSize = properties.getInt("fontSize"),
                contentPadding = properties.getInt("contentPadding"),
                roundedCornerShapeSize = properties.getInt("roundedCornerShapeSize"),
                textColor = properties.getString("textColor")
            )
        }

        ComponentViewType.Spacer -> {
            GenericSpacer(size = textSize(properties.getInt("height")))
        }

        ComponentViewType.TextField -> {
            var text by rememberSaveable { mutableStateOf("") }
            GenericTextField(
                value = text,
                onValueChange = { text = it },
                modifier = textPaddingStart(properties.getInt("padding")),
                label = properties.getString("label"),
                roundedCornerShapeSize = properties.getInt("roundedCornerShapeSize"),
                colorCode = properties.getString("colorCode"),
                placeholder = properties.getString("placeholder")
            )
        }
        ComponentViewType.OtpTextField -> {
            OtpInputComponent(
                viewWidth = properties.getInt("width"),
                viewHeight = properties.getInt("height"),
                otpLength = properties.getInt("otpLength"),
                viewPadding = properties.getInt("padding"),
                rowSpaceBetween = properties.getInt("rowSpaceBetween"),
                roundedCornerShapeSize = properties.getInt("roundedCornerShapeSize"),
                onOtpComplete = { otpValue ->
                Log.d("OTP", "Entered OTP: $otpValue")
            })
        }
    }
}
