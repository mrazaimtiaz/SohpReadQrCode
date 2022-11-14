package com.gicproject.salamattendanceapp.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.camera.core.AspectRatio.RATIO_16_9
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.gicproject.salamattendanceapp.R
import com.gicproject.salamattendanceapp.Screen
import com.gicproject.salamattendanceapp.common.Constants
import com.gicproject.salamattendanceapp.data.remote.dto.EmployeeDto
import kotlinx.coroutines.delay
import java.io.File
import java.util.*
import java.util.concurrent.Executor

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun UserInputScreen(
    navController: NavController,
    viewModel: MyViewModel,
    outputDirectory: File,
    employeeDto: EmployeeDto?,
    executor: Executor,
) {
    val state = viewModel.stateUserInput.value
    
    //initalize camera
    //todo for new  android 9.0 prodvx 10 inch new  CameraSelector.LENS_FACING_BACK
    // for old android 8.0 prodvx 10 inch  CameraSelector.LENS_FACING_FRONT
     val lensFacing = CameraSelector.LENS_FACING_FRONT
     val context = LocalContext.current
     val lifecycleOwner = LocalLifecycleOwner.current
 
     val preview = androidx.camera.core.Preview.Builder().build()
     val previewView = remember { PreviewView(context) }
   // previewView.rotation = 270f
     val imageCapture: ImageCapture = remember { ImageCapture.Builder()
         .setTargetAspectRatio(RATIO_16_9).build() }
     val cameraSelector = CameraSelector.Builder()
         .requireLensFacing(lensFacing)
         .build()

    val closeScreen = viewModel.resetScreens.value

    LaunchedEffect(key1 = true ){
        delay(3000)
        takePhoto(
            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
            imageCapture = imageCapture,
            outputDirectory = outputDirectory,
            executor = executor,
            onImageCaptured = {
                viewModel.setShowCamera(false)
                viewModel.setPhotoUri(it)
                viewModel.setShowPhoto(true)
                viewModel.showEmployeeInfoScreen(true,context,employeeDto?.employeeNumber ?: "-99")
            },
            onError = {
                Log.e("kilo", "View error:", it)
                viewModel.setCameraInitialized(false)
            }
        )
    }
 /*   LaunchedEffect(true) {
        delay(15000)
        navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
    }
    if(closeScreen){
        navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
    }*/
    LaunchedEffect(key1 = true) {

        Log.d("TAG", "FingerPrintPage: true")
        //  if (!viewModel.isCameraInitialized.value) {
        val cameraProvider = context.getCameraProvider(onError = {
            Log.d("TAG", "FingerPrintPage: INITALZED ERROR true")
            viewModel.setCameraInitialized(false)
        }, onSucess = {
            Log.d("TAG", "FingerPrintPage:INITALZED true")
            viewModel.setCameraInitialized(true)
        })
        cameraProvider.unbindAll()

        try {
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
            Log.d("TAG", "TestCameraScreen: job complete")
        } catch (e: Exception) {
        }

        //    }
    }

    preview.setSurfaceProvider(previewView.surfaceProvider)



    if (state.success.isNotBlank()) {
        LaunchedEffect(Unit) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_SUCCESS_MSG, state.success
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                Constants.STATE_EMPLOYEE_INFO, employeeDto
            )
            navController.navigate(Screen.EmployeeInfoScreen.route)
        }

    }

    Scaffold(
        modifier = Modifier.background(color = Color.White)
    ) { innerPadding ->
        Modifier.padding(innerPadding)
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            /* Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(20.dp),
             ) {
                 IconButton(
                     onClick = {
                   //      navController.popBackStack()
                     },
                 ) {
                     Icon(
                         Icons.Filled.ArrowBack,
                         "contentDescription",
                         modifier = Modifier.size(150.dp),
                         tint = Color.Black
                     )
                 }
             }*/

            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                            Row(
                                Modifier
                                    .padding(horizontal = 20.dp, vertical = 20.dp)
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                if (viewModel.shouldShowCamera.value) {
                                    AndroidView(
                                        {
                                            previewView }, modifier = Modifier
                                            .size(500.dp, 500.dp)  //500 7inch tablet
                                            .rotate(-90f)
                                            .border(
                                                2.dp,
                                                MaterialTheme.colors.primary,
                                                shape = RoundedCornerShape(10.dp)
                                            ))
                                }
                                if (viewModel.shouldShowPhoto.value) {
                                    Image(
                                        painter = rememberAsyncImagePainter(viewModel.photoUri.value),
                                        contentDescription = "Image",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .size(500.dp, 500.dp) //500 7inch tablet
                                            .rotate(-90f)
                                            .border(
                                                2.dp,
                                                MaterialTheme.colors.primary,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    )
                                }
                            }


                                Column(
                                    Modifier
                                        .padding(horizontal = 20.dp, vertical = 20.dp)
                                        .weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Column {
                                        var bitmap: ImageBitmap? = null
                                        if (employeeDto?.image != null) {
                                            try {
                                                bitmap = employeeDto.image!!.toBitmap().asImageBitmap()
                                            } catch (e: java.lang.Exception) {
                                                bitmap = null
                                            }
                                        }
                                        bitmap?.let {
                                            Image(
                                                bitmap = it,
                                                contentDescription = "Image",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(200.dp, 200.dp)
                                                    .clip(CircleShape)
                                                    .border(
                                                        4.dp,
                                                        MaterialTheme.colors.primary,
                                                        CircleShape
                                                    )
                                            )
                                        }

                                    }
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(20.dp)
                                    ) {
                                        Spacer(Modifier.height(10.dp))
                                        TextInfo("Employee ID: ", employeeDto?.employeeNumber?.toString() ?: "",)
                                        Spacer(Modifier.height(10.dp))
                                        TextInfo("Employee Name: ", employeeDto?.fullNameEn ?: "")
                                        Spacer(Modifier.height(10.dp))
                                       // TextInfo("Designation: ", employeeDto?.departmentEn ?: "")
                                       // Spacer(Modifier.height(10.dp))
                                    }
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                    }
                                }
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
                        navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
                    }

                }
                if(state.isLoading){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }

                 }else{
                   /* Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            Log.d("TAG", "UserInputScreen: called checkin")
                            takePhoto(
                                filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                imageCapture = imageCapture,
                                outputDirectory = outputDirectory,
                                executor = executor,
                                onImageCaptured = {
                                    viewModel.setShowCamera(false)
                                    viewModel.setPhotoUri(it)
                                    viewModel.setShowPhoto(true)
                                    viewModel.showEmployeeInfoScreen(true,context,employeeDto?.employeeNumber ?: "-99")
                                },
                                onError = {
                                    Log.e("kilo", "View error:", it)
                                    viewModel.setCameraInitialized(false)
                                }
                            )
                        }, modifier = Modifier.size(400.dp,80.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Check In", fontSize = 40.sp)
                                Icon(
                                    Icons.Default.ArrowBack,
                                    "contentDescription",
                                    modifier = Modifier.size(60.dp),
                                )

                            }
                        }
                        OutlinedButton(onClick = {
                            takePhoto(
                                filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                imageCapture = imageCapture,
                                outputDirectory = outputDirectory,
                                executor = executor,
                                onImageCaptured = {
                                    viewModel.setShowCamera(false)
                                    viewModel.setPhotoUri(it)
                                    viewModel.setShowPhoto(true)
                                    viewModel.showEmployeeInfoScreen(false,context,employeeDto?.employeeNumber ?: "-99")
                                },
                                onError = {
                                    Log.e("kilo", "View error:", it)
                                    viewModel.setCameraInitialized(false)
                                }
                            )
                        }, modifier = Modifier.size(400.dp,80.dp),  border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Check Out", fontSize = 40.sp)
                                Icon(
                                    Icons.Filled.ArrowForward,
                                    "contentDescription",
                                    modifier = Modifier.size(60.dp),
                                )

                            }
                        }
                    }*/
                }

            }
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
            Row(
                modifier = Modifier
                    .background(color = Color.White),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                IconButton(onClick = {
                    navController.popBackStack(route = Screen.MainScreen.route, inclusive = false)
                }) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        "back arrow",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(60.dp, 60.dp)
                    )
                }
            }
         /*   val hour = remember { mutableStateOf(0) }
            val minute = remember { mutableStateOf(0) }
            val second = remember { mutableStateOf(0) }
            val am_pm = remember { mutableStateOf(0) }
            LaunchedEffect(key1 = Unit, block = {
                while (true) {
                    delay(100)
                    setTimes(hour, minute, second, am_pm = am_pm)
                }
            })
            WallClock(hour.value,
                minute.value,
                second.value,am_pm.value)*/
        }

    }
}

@Composable
fun Pulsating(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}

fun String.toBitmap(): Bitmap {
    Base64.decode(this, Base64.DEFAULT).apply {
        return BitmapFactory.decodeByteArray(this, 0, size)
    }
}

