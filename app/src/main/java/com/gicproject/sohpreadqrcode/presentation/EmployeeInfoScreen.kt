package com.gicproject.sohpreadqrcode.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gicproject.sohpreadqrcode.Screen
import com.gicproject.sohpreadqrcode.common.Constants.Companion.doneJson
import com.gicproject.sohpreadqrcode.data.remote.dto.EmployeeDto
import com.gicproject.sohpreadqrcode.ui.theme.darkGreen
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EmployeeInfoScreen(
    navController: NavController,
    viewModel: MyViewModel,
    employeeInfo: EmployeeDto?,
    message: String?,
) {
    val closeScreen = viewModel.resetScreens.value


    LaunchedEffect(true) {
        delay(5000)
        navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
    }
    if(closeScreen){
            navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
    }



    Scaffold(
        modifier = Modifier.background(color = Color.White)
    ) { innerPadding ->
        Modifier.padding(innerPadding)
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                         message ?: "Checked In Successful",
                        color = darkGreen,
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp
                    )
                    DoneAnimation()
                }
                Row(
                    Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column {

                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Spacer(Modifier.height(10.dp))
                        TextInfo("Employee Code: ", employeeInfo?.employeeNumber?.toString() ?: "")
                        Spacer(Modifier.height(10.dp))
                        TextInfo("Employee Name: ", employeeInfo?.fullNameEn ?: "")
                        Spacer(Modifier.height(10.dp))
                      //  TextInfo("Designation: ", employeeInfo?.departmentEn ?: "")
                     //   Spacer(Modifier.height(10.dp))
                        // variable for simple date format.
                        val sdf = SimpleDateFormat("hh:mm:ss aa")

                        val currentDateAndTime = sdf.format(Date())

                        TextInfo("CheckIn Time: ", " $currentDateAndTime")
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                    }
                }
            }
        }

    }
}

@Composable
fun DoneAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(doneJson))
    LottieAnimation(
        composition, modifier = Modifier
            .size(100.dp)
    )
}

@Composable
fun TextInfo(title: String, value: String) {
    Text(text = buildAnnotatedString {
        pushStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, //20  7inch tablet
                color = MaterialTheme.colors.primary
            )
        )
        append(title)
        pushStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,//20 7inch tablet
                color = Color.Black
            )
        )
        append(value)
    })
}