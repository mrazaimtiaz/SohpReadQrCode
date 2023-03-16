package com.gicproject.sohpreadqrcode.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavController
import com.gicproject.sohpreadqrcode.R
import com.gicproject.sohpreadqrcode.Screen
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.domain.model.ResultClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun InsertOtpScreen(
    navController: NavController,
    viewModel: MyViewModel,
    resultClass: ResultClass?,
) {

    val state = viewModel.stateInsertOtp.value
    val listState = rememberLazyListState()

    val second = remember { mutableStateOf(120) }

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
                    text = "Please Insert OTP Sent to your Mobile Number",
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
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        }else{
                            Button(onClick = {
                                if (textMobileNumber.value.isNotBlank()) {
                                    if(textMobileNumber.value == resultClass?.MessageAr){
                                        viewModel.onEvent(MyEvent.EmployeeInfoCode(resultClass.ID.toString()))
                                       /* navController.currentBackStackEntry?.savedStateHandle?.set(
                                            Constants.STATE_EMPLOYEE_INFO, EmployeeDto(employeeNumber = resultClass.ID.toString())
                                        )
                                        navController.navigate(Screen.UserInputScreen.route)*/
                                    }else{
                                        Toast.makeText(context, "Wrong OTP Code", Toast.LENGTH_SHORT)                         .show()
                                    }
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
                                Text("Verify OTP",  fontSize = 30.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }

                    if (state.error.isNotBlank()) {
                        Text(state.error, color = MaterialTheme.colors.error)
                    }
                }
            }
            if(state.success.isNotBlank()){
                LaunchedEffect(key1 = Unit, block = {
                    CoroutineScope(Dispatchers.Main).launch {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            Constants.STATE_EMPLOYEE_INFO, state.employeeDto
                        )
                        navController.navigate(Screen.EmployeeInfoScreen.route)
                    }
                })

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







