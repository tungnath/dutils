import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    // Initial filePath
    var filePath by remember { mutableStateOf("") }

    MaterialTheme {

        Column(

            modifier = Modifier
//                .width(IntrinsicSize.Max)
                .fillMaxSize()
//                .padding(24.dp)
                .background(Color.LightGray)
//                .border(0.5.dp, Color.DarkGray)
                .clickable { println("Column clicked") },
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Desktop Utils",
                style = MaterialTheme.typography.h3,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.2f).padding(8.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
//                    .wrapContentHeight(align = Alignment.CenterVertically)
//                    .background(Color.Yellow)
//                    .border(1.dp, Color.Black)
                    .clickable { println("Title clicked") },
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,

                )


            Column(
                Modifier.fillMaxSize(fraction = 0.9f).padding(8.dp),
//                .background(Color.Yellow),

                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    modifier = Modifier
//                .fillMaxWidth(fraction = 0.5f)
                        .fillMaxSize(fraction = 0.8f).padding(8.dp), elevation = 16.dp, shape = RoundedCornerShape(8.dp)
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp)
//                        .background(Color.LightGray)
//                        .border(1.dp, Color.DarkGray)
                            .clickable { println("Column clicked") },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Text(
                            text = "Locate the file/directory",
                            style = MaterialTheme.typography.h5,
                            color = Color.Blue,
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
//                    .background(Color.Yellow)
//                    .border(1.dp, Color.Black)
                                .clickable { println("Text clicked") },
                            fontFamily = myCustomFontFamily,
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                            onClick = {

                                // TODO add capability to launch Desktop file browser and capture its path in field
                                //  $filePath
                                println("File browser clicked")
                            }

                        ) {
                            Text("Browse")
                        }

                        // hide this initially
                        Text(
                            text = filePath,
                            style = MaterialTheme.typography.body1,
                            color = Color.DarkGray,
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            fontFamily = myCustomFontFamily,
                        )

                        MyInputTextField("Add the tag")

                        MyButton("Tag It!!", onButtonClick = {
                            println("Tag It Button clicked! and the path is $filePath")
                        })

                    }

                }

            }


        }


    }

}


val myCustomFontFamily = FontFamily(
    Font("fonts/fontawesome.ttf", FontWeight.Normal),
    Font("fonts/lato_regular.ttf", FontWeight.Normal)
)


@Composable
fun MyInputTextField(hintText: String) {
//    var text by remember { mutableStateOf("") }
//
//    TextField(
//        value = text,
//        onValueChange = { newText -> text = newText },
//        label = { Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = hintText,
//        ) }
//    )


    var text by remember { mutableStateOf("") }

    OutlinedTextField(value = text, onValueChange = {
        text = it
    }, label = {
        Text(hintText)
    }, modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp),)

}

@Composable
fun MyButton(btnText: String = "Click Me", onButtonClick: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(8.dp),
        onClick = { onButtonClick() }) {
        Text(text = btnText)
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "dUTILS") {
        App()
    }
}



