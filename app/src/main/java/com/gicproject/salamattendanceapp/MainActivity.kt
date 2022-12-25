package com.gicproject.salamattendanceapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gicproject.salamattendanceapp.common.Constants
import com.gicproject.salamattendanceapp.data.remote.dto.EmployeeDto
import com.gicproject.salamattendanceapp.led.LampsUtil
import com.gicproject.salamattendanceapp.presentation.*
import com.gicproject.salamattendanceapp.ui.theme.ClassRoomAttendanceTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val barcode = StringBuffer()

    private var viewModel: MyViewModel? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
            viewModel?.setShowCamera(true)
        } else {
            Log.i("kilo", "Permission denied")
        }
    }

    //working barcode
    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            viewModel?.showUserInputScreen(barcode.toString())
            barcode.delete(0, barcode.length)
        }
        return super.dispatchKeyEvent(event)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var lamp = LampsUtil()
        setContent {
            viewModel = hiltViewModel()
            val darkTheme = viewModel!!.isDarkTheme.value
            val systemUiController = rememberSystemUiController()

            //testing
        /*    LaunchedEffect(key1 = true ){
                delay(5000L)
                viewModel?.showStudentScreen()
            }*/


            ClassRoomAttendanceTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()

                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colors.primary
                )
                Surface(color = MaterialTheme.colors.surface) {
                    NavHost(
                        navController = navController,
                        //
                        //startDestination = Screen.TestCameraScreen.route
                            startDestination =  Screen.MainScreen.route
                    ) {
                        composable(
                            route = Screen.MainScreen.route
                        ) {
                            viewModel!!.initGetMain()
                            MainScreen(
                                navController, viewModel = viewModel!!,
                            )
                        }
                        composable(
                            route = Screen.UserInputScreen.route
                        ) {
                            viewModel!!.initUserInput()
                            val employeeInfo = navController.previousBackStackEntry?.savedStateHandle?.get<EmployeeDto?>(
                                Constants.STATE_EMPLOYEE_INFO)
                            UserInputScreen(
                                navController,
                                viewModel!!,
                                outputDirectory,
                                executor = cameraExecutor,
                                employeeDto = employeeInfo,
                            )
                        }
                        composable(
                            route = Screen.EmployeeInfoScreen.route
                        ) {
                            val employeeInfo = navController.previousBackStackEntry?.savedStateHandle?.get<EmployeeDto?>(
                                Constants.STATE_EMPLOYEE_INFO)

                            val messsage = navController.previousBackStackEntry?.savedStateHandle?.get<String?>(
                                Constants.STATE_SUCCESS_MSG)
                            EmployeeInfoScreen(
                                navController,
                                viewModel!!,
                                employeeInfo = employeeInfo,
                                messsage
                            )
                        }
                        composable(
                            route = Screen.TestCameraScreen.route
                        ) {
                            TestCameraScreen(
                                navController,
                                viewModel!!,
                                executor = cameraExecutor,
                                outputDirectory = outputDirectory,
                            )
                        }
                        composable(
                            route = Screen.DemoScreen.route
                        ) {
                            DemoScreen(
                                navController,
                                viewModel!!,
                            )
                        }
                        composable(
                            route = Screen.QrReaderScreen.route
                        ) {
                            QrReaderScreen(
                                navController,
                                viewModel!!,
                            )
                        }
                        composable(Screen.SettingScreen.route) {
                            SettingScreen(navController,viewModel!!)
                        }
                        composable(Screen.InsertMobileNumberScreen.route) {
                            InsertMobileNumberScreen(navController,viewModel!!)
                        }
                    }
                }
            }
            requestCameraPermission()
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onResume() {
        requestCameraPermission()
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel?.setShowCamera(true)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

