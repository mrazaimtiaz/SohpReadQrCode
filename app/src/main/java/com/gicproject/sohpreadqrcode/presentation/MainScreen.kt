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

/*    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate(Screen.UserInputScreen.route)
    }*/


    if (state.success.isNotBlank()) {
        LaunchedEffect(Unit) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_EMPLOYEE_INFO, state.employeeInfo
            )
           navController.navigate(Screen.EmployeeInfoScreen.route)
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
                    ){
                        var color =  MaterialTheme.colors.primary
                        Box() {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = color),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("  1  ",
                                    modifier= Modifier.padding(0.dp),
                                    color = Color.White, fontSize =40.sp,
                                    fontWeight = FontWeight.Bold)
                            }

                        }
                        Spacer(modifier = Modifier.width(50.dp))
                        if(state.error.isNotBlank()){
                            val context =  LocalContext.current
                            LaunchedEffect(key1 = true ){
                                Toast.makeText(
                                    context,
                                    state.error,
                                    Toast.LENGTH_LONG
                                ).show()
                                viewModel.emptyToast()
                            }
                        }
                        Text(
                            text = "${state.error} Please Scan a QR code",
                            color = MaterialTheme.colors.primary, fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.sampleqrcode),
                        modifier = Modifier.size(200.dp),
                        contentDescription = "qrcode sample"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "OR",
                        color = Color.Black, fontSize = 60.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                    ){
                        var color =  MaterialTheme.colors.primary
                        Box() {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = color),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("  2  ",
                                    modifier= Modifier.padding(0.dp),
                                    color = Color.White, fontSize =40.sp,
                                    fontWeight = FontWeight.Bold)
                            }

                        }
                        Spacer(modifier = Modifier.width(50.dp))
                        Text(
                            text = "Send OTP to your Mobile Number",
                            color = MaterialTheme.colors.primary, fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " / ",
                            color =Color.Black, fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                                text = "Use PIN Code",
                        color = MaterialTheme.colors.primaryVariant, fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = {
                            navController.navigate(Screen.InsertIdScreen.route)},
                            modifier = Modifier
                                .padding(20.dp)
                                .shadow(50.dp, shape = RoundedCornerShape(5.dp)),
                            shape = RoundedCornerShape(30.dp),
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
                        Button(onClick = {
                            navController.navigate(Screen.InsertIdScreen.route)},
                            modifier = Modifier
                                .padding(20.dp)
                                .shadow(50.dp, shape = RoundedCornerShape(5.dp)),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                            shape = RoundedCornerShape(30.dp),
                        ) {
                            Icon(
                                Icons.Default.Send,
                                contentDescription = "",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text("PIN Code",  fontSize = 30.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                    if(state.isLoading){
                        CircularProgressIndicator()
                    }
                    if(state.error.isNotBlank()){
                        val context =  LocalContext.current
                        LaunchedEffect(key1 = true ){
                            Toast.makeText(
                                context,
                                state.error,
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.emptyToast()
                        }
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
        Text(text = "$hourString$hour:$minuteString$minute:$secondString$second $am_pmString", color = MaterialTheme.colors.primary, fontSize = 35.sp, textAlign = TextAlign.End, modifier = Modifier.padding(10.dp))
    }
}

