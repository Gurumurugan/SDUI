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
import com.sdui.ui.theme.ModifierSize
import com.sdui.ui.theme.SDUITheme
import com.sdui.ui.theme.TextFont
import com.sdui.ui.theme.TextFontSize
import com.sdui.ui.theme.TextPaddingStart
import com.sdui.ui.theme.TextSize
import com.sdui.ui.theme.colorSet
import com.sdui.ui.theme.getAlignment
import com.sdui.ui.theme.getTextAlignment
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
    // Use LaunchedEffect to delay navigation
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        getJsonData("https://stgwww.luv.com/main_sdui/sdui.json",context)
        val jsonString = readJsonFromRaw(context, R.raw.sdui)
       // getData(jsonString)
        delay(3000)
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

    // Access the `screen_two` array
    val screenTwoArray = rootObject.getJSONArray("screen_two")
    // Access the first object in the `screen_two` array
    val screenTwoObject = screenTwoArray.getJSONObject(0)
    // Access `screen_two_layout` array
    val screenTwoLayoutArray = screenTwoObject.getJSONArray("screen_two_layout")
    // Access the first layout object
    val firstLayoutObject = screenTwoLayoutArray.getJSONObject(0)
    baseView = firstLayoutObject.getString("type")
    val baseViewProperties = firstLayoutObject.getJSONObject("properties")

    // Access `screen_two_children` array
     screenTwoChildrenArray = firstLayoutObject.getJSONArray("screen_two_children")


    // Access the `screen_two` array
    val screenThreeArray = rootObject.getJSONArray("screen_three")
    // Access the first object in the `screen_two` array
    val screenThreeObject = screenThreeArray.getJSONObject(0)

    // Access `screen_two_layout` array
    val screenThreeLayoutArray = screenThreeObject.getJSONArray("screen_three_layout")
    // Access the first layout object
    val twoLayoutObject = screenThreeLayoutArray.getJSONObject(0)
    baseView = twoLayoutObject.getString("type")
    // Access `screen_two_children` array
    screenThreeChildrenArray = twoLayoutObject.getJSONArray("screen_three_children")


    // Access the `screen_two` array
    val screenFourArray = rootObject.getJSONArray("screen_four")
    // Access the first object in the `screen_two` array
    val screenFourObject = screenFourArray.getJSONObject(0)
    // Access `screen_two_layout` array
    val screenFourLayoutArray = screenFourObject.getJSONArray("screen_four_layout")
    // Access the first layout object
    val fourLayoutObject = screenFourLayoutArray.getJSONObject(0)
    baseView = fourLayoutObject.getString("type")
    // Access `screen_two_children` array
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
    when(type){
        "Column" -> {
            GenericColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                jsonArray  = jsonArray
            ) { item ->
                ComponentView(type = item.getString("type"),properties = item.getJSONObject("properties"),navController)
            }
        }
        "Row" -> {
            GenericRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                jsonArray  = screenTwoChildrenArray!!
            ) { item ->
                ComponentView(type = item.getString("type"),properties = item.getJSONObject("properties"),navController)
            }
        }
        "Box" ->{
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
    when (type) {
        "Image" -> {
            GenericImage(
                imageModel = properties.getString("image"),
                modifier = ModifierSize(properties.getString("height"),properties.getString("padding")),
                alignment = getAlignment(properties.getString("alignment")),
                contentScale = ContentScale.Crop
            )
        }
        "Text" -> {
                GenericText(
                    text = properties.getString("text"),
                    textAlign = getTextAlignment(properties.getString("textAlign")),
                    modifier = TextPaddingStart(properties.getString("padding")),
                    color = colorSet(properties.getString("color")),
                    fontSize = TextFontSize(properties.getString("fontSize")),
                    fontWeight = TextFont(properties.getString("fontWeight"))
                )
        }
        "Button" -> {
            GenericButton(
                onClick = { navController.navigate(properties.getString("navigate")) },
                text = properties.getString("text"),
                modifier = TextPaddingStart(properties.getString("padding")),
                colors = buttonColors(colorResource(id = R.color.app)),
                buttonHeight = properties.getString("height"),
                buttonWidth = properties.getString("width"),
                fontSize = properties.getString("fontSize"),
                contentPadding = properties.getString("contentPadding"),
                roundedCornerShapeSize = properties.getString("roundedCornerShapeSize"),
                textColor = properties.getString("textColor")
            )
        }

        "Spacer" -> {
            GenericSpacer(size = TextSize(properties.getString("height")))
        }

        "TextField" -> {
            var text by rememberSaveable { mutableStateOf("") }
            GenericTextField(
                value = text,
                onValueChange = { text = it },
                modifier = TextPaddingStart(properties.getString("padding")),
                label = properties.getString("label"),
                roundedCornerShapeSize = properties.getString("roundedCornerShapeSize"),
                colorCode = properties.getString("colorCode"),
                placeholder = properties.getString("placeholder")
            )
        }
    }
}
