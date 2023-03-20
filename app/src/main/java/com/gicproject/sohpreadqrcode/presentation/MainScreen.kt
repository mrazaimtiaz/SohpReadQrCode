package com.gicproject.sohpreadqrcode.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavController
import com.gicproject.sohpreadqrcode.R
import com.gicproject.sohpreadqrcode.Screen
import com.gicproject.sohpreadqrcode.common.Constants
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MyViewModel,
) {
    val state = viewModel.stateMain.value

    val kwlogo = R.drawable.kwlogo


    val accreditedlogo = R.drawable.accreditedlogo

    val sohpenlogo =R.drawable.sohpenlogo

    val sohparlogo = R.drawable.sohparlogo



     val imagelogo = remember{ mutableStateOf(sohparlogo)}

/*    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate(Screen.UserInputScreen.route)
    }*/


    if (state.success.isNotBlank()) {
        LaunchedEffect(Unit) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_IS_SUCCESS, true
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_MSG, state.success
            )
           navController.navigate(Screen.MessageInfoScreen.route)
      }
    }
    if (state.error.isNotBlank()) {
        LaunchedEffect(Unit) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_IS_SUCCESS, false
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_MSG, state.error
            )
            navController.navigate(Screen.MessageInfoScreen.route)
        }
    }


    Scaffold(
        modifier = Modifier.background(color = Color.White)
    ) { innerPadding ->
        Modifier.padding(innerPadding)
    }

    Box(   modifier = Modifier
        .fillMaxSize()) {

        Column(
            modifier = Modifier
                .background(color = Color.White)
        ) {

            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = imagelogo.value ),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(100.dp)
                        .height(55.dp)
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

            Row(modifier = Modifier.fillMaxSize(),) {
                Column(
                    modifier = Modifier
                        .weight(2.0f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Text(
                            text = "Please Scan a QR code",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary, fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                     /*   if(state.error.isNotBlank()){
                            val context =  LocalContext.current
                            LaunchedEffect(key1 = true ){
                                Toast.makeText(
                                    context,
                                    state.error,
                                    Toast.LENGTH_LONG
                                ).show()
                                viewModel.emptyToast()
                            }
                        }*/
                        Text(
                            text = "يرجى مسح رمز الاستجابة السريعة ضوئيًا",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary, fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.sampleqrcode),
                        modifier = Modifier.size(150.dp),
                        contentDescription = "qrcode sample"
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if(state.isLoading){
                        CircularProgressIndicator()
                    }

                    }
                }
                /*Column(
                    modifier = Modifier
                        .weight(2.0f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = com.gicproject.salamattendanceapp.R.drawable.qrcode),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                    )
                    Text(
                        text = "For Mobile App",
                        color = MaterialTheme.colors.primary, fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }*/
            }
        }

    val hour = remember { mutableStateOf(0) }
    val minute = remember { mutableStateOf(0) }
    val second = remember { mutableStateOf(0) }
    val am_pm = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(4000)
            when (imagelogo.value) {
                sohparlogo -> imagelogo.value = sohpenlogo
                sohpenlogo -> imagelogo.value = accreditedlogo
                accreditedlogo -> imagelogo.value = kwlogo
                kwlogo -> imagelogo.value = sohparlogo
            }
        }
    })
    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(100)
            setTimes(hour, minute, second,am_pm)
        }

    })
    WallClock(hour.value,
        minute.value,
        second.value,am_pm.value)

}


fun setTimes(
    hour: MutableState<Int>,
    minute: MutableState<Int>,
    second: MutableState<Int>,
    am_pm: MutableState<Int>,
) {
    val calendar = Calendar.getInstance()
    hour.value = calendar.get(Calendar.HOUR)
    minute.value = calendar.get(Calendar.MINUTE)
    second.value = calendar.get(Calendar.SECOND)
    am_pm.value = calendar.get(Calendar.AM_PM)
    Log.d("TAG", "setTimes: am or mp ${am_pm.value}")
}


@Composable
fun WallClock(hour: Int, minute: Int, second: Int,am_pm: Int, modifier: Modifier = Modifier) {
    var minuteString = ""
    if(minute < 10){
        minuteString = "0"
    }

    var secondString = ""
    if(second < 10){
        secondString = "0"
    }

    var hourString = ""
    if(hour < 10){
        hourString = "0"
    }
    var am_pmString = "AM"
    if(am_pm == 1){
        am_pmString = "PM"
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Top) {
        Text(text = "$hourString$hour:$minuteString$minute:$secondString$second $am_pmString", color = MaterialTheme.colors.primary, fontSize = 18.sp, textAlign = TextAlign.End, modifier = Modifier.padding(10.dp))
    }
}

