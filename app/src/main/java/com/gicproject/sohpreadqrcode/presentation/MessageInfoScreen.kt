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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gicproject.sohpreadqrcode.R
import com.gicproject.sohpreadqrcode.Screen
import com.gicproject.sohpreadqrcode.common.Constants.Companion.doneJson
import com.gicproject.sohpreadqrcode.common.Constants.Companion.errorJson
import com.gicproject.sohpreadqrcode.domain.model.PatientInfo
import com.gicproject.sohpreadqrcode.ui.theme.darkGreen
import com.gicproject.sohpreadqrcode.ui.theme.redTable
import kotlinx.coroutines.delay

@Composable
fun MessageInfoScreen(
    navController: NavController,
    viewModel: MyViewModel,
    isSuccess: Boolean?,
    message: String?,
    patientInfo: PatientInfo?,
) {


    LaunchedEffect(true) {
        delay(5000)
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                         message ?: "",
                        color = if(isSuccess == true) darkGreen else redTable,
                        fontFamily = FontFamily(Font(R.font.ge_bold)),
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if(isSuccess == true){
                        DoneAnimation()

                    }else{
                        ErrorAnimation()
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Spacer(Modifier.height(10.dp))
                    TextInfo("Name: ", patientInfo?.Name?.toString() ?: "")
                    Spacer(Modifier.height(10.dp))
                    TextInfo("Civil ID: ", patientInfo?.CivilID ?: "")
                    Spacer(Modifier.height(10.dp))
                    TextInfo("Appointment Date: ", patientInfo?.SDate ?: "")
                    Spacer(Modifier.height(10.dp))
                    TextInfo("Appointment Time: ", patientInfo?.Time ?: "")
               //     Spacer(Modifier.height(10.dp))
                    //  TextInfo("Designation: ", employeeInfo?.departmentEn ?: "")
                    //   Spacer(Modifier.height(10.dp))
                    // variable for simple date format.

                 //   TextInfo("CheckIn Time: ", " $currentDateAndTime")
                }

            }

            }
        }

    }


@Composable
fun ErrorAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(errorJson))
    LottieAnimation(
        composition, modifier = Modifier
            .size(100.dp)
    )
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
