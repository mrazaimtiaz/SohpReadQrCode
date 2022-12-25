package com.gicproject.salamattendanceapp.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gicproject.salamattendanceapp.R
import com.gicproject.salamattendanceapp.Screen
import com.gicproject.salamattendanceapp.common.Constants.Companion.heartBeatJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InsertMobileNumberScreen(
    navController: NavController,
    viewModel: MyViewModel,
) {

    val listState = rememberLazyListState()

    val second = remember { mutableStateOf(80) }

    var textMobileNumber = remember { mutableStateOf("") }

    var showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DoneDialog(showDialog)
    }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(1000)
            second.value = second.value - 1
            if (second.value == 0) {
                navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
            }
        }
    })
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.surface,
                )
        ) {
            Modifier.padding(innerPadding)
            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(290.dp)
                        .height(65.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                if (change.position.y > 400) {
                                    navController.navigate(Screen.SettingScreen.route)
                                }
                                change.consume()
                            }
                        },
                    contentDescription = "qrcode sample"
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(20.dp)
                ) {
                   GoBack(navController)
                }
            }

            HeartBeatTime(second = second)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Insert Your Employee ID",
                    color = MaterialTheme.colors.primary, fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(400.dp)
                            .background(
                                MaterialTheme.colors.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {



                        Text(
                            textMobileNumber.value,
                            color = Color.White,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var context = LocalContext.current
                    Row() {
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "1"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "1")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "2"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "2")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "3"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "3")
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row() {
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "4"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "4")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "5"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "5")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "6"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "6")
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row() {
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "7"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "7")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "8"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "8")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "9"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "9")
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row() {
                        NumberKeypad({
                            textMobileNumber.value = ""
                        }, isIconClose = true)
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.length != 8) {
                                textMobileNumber.value = textMobileNumber.value + "0"
                            } else {
                                //Toast.makeText(context, "Current Length 8", Toast.LENGTH_SHORT)
 //                                   .show()
                            }
                        }, "0")
                        Spacer(modifier = Modifier.width(10.dp))
                        NumberKeypad({
                            if (textMobileNumber.value.isNotBlank()) {
                                textMobileNumber.value =
                                    textMobileNumber.value.substring(0, textMobileNumber.value.length - 1);
                            }

                        }, isIconBack = true)
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {
                            showDialog.value = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                navController.popBackStack(Screen.MainScreen.route,false)
                            }
                                         },
                            modifier = Modifier
                                .padding(20.dp)
                                .shadow(50.dp, shape = RoundedCornerShape(5.dp)),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
                            ) {
                            Icon(
                                Icons.Default.Send,
                                contentDescription = "",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text("Send OTP",  fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }

            }


            /* if (state.error.isNotBlank()) {

             }
             if (state.isLoading) {
                 CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
             }
             if (state.success.isNotBlank()) {
                 LaunchedEffect(key1 = true) {

                 }
             }*/
        }
    }
}


@Composable
fun NumberKeypad(
    onClick: () -> Unit,
    text: String = "",
    isIconClose: Boolean = false,
    isIconBack: Boolean = false
) {
    Button(
        onClick = onClick,
        colors = if (isIconClose || isIconBack) {
            ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
        } else {
            ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        },
        modifier = Modifier
            .width(65.dp)
            .height(65.dp)
            .shadow(20.dp, shape = CircleShape),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isIconClose) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier.size(50.dp)
                )
            } else if (isIconBack) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(50.dp)
                )
            } else {

                Text(
                    text, fontSize = 28.sp,  textAlign = TextAlign.Center
                )
            }
        }

    }
}


@Composable
fun HeartBeatAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(heartBeatJson))
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.size(80.dp),
        isPlaying = true,

        )
}


@Composable
fun HeartBeatTime(second: MutableState<Int>){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeartBeatAnimation()
            Spacer(modifier = Modifier.width(20.dp))
            Text(second.value.toString(), fontSize = 40.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Composable
fun GoBack(navController: NavController){
    Button(onClick = {    navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)},
        modifier = Modifier
            .padding(10.dp)
            .shadow(50.dp, shape = RoundedCornerShape(5.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        shape = RoundedCornerShape(25.dp),

    ) {
        Icon(
            Icons.Default.KeyboardArrowLeft,
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Go Back",  fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DoneDialog(showDialog: MutableState<Boolean>){
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = {
            showDialog.value = false
        },

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(vertical = 80.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "OTP Send to your Mobile Number",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp
            )
            DoneAnimation()
        }
    }
}





