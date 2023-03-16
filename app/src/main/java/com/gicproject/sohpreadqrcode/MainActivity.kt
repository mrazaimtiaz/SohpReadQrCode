package com.gicproject.sohpreadqrcode

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.data.remote.dto.EmployeeDto
import com.gicproject.sohpreadqrcode.domain.model.ResultClass
import com.gicproject.sohpreadqrcode.presentation.*
import com.gicproject.sohpreadqrcode.ui.theme.ClassRoomAttendanceTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.telpo.tps550.api.serial.Serial
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val barcode = StringBuffer()

    private var viewModel: MyViewModel? = null

    private var stringBuilder: StringBuilder? = null

    private var serial: Serial? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    var flag = true



    //working barcode
    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            viewModel?.setBarcode("11bar${barcode.toString()}")
            viewModel?.showUserInputScreen(barcode.toString())
            barcode.delete(0, barcode.length)
        }
        return super.dispatchKeyEvent(event)
    }

    private fun initSerial(serialpath: String) {
        try {
            serial = Serial(serialpath, 9600, 0) //ttyHSL1
            inputStream = serial?.inputStream
            outputStream = serial?.outputStream
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSerial("/dev/ttyACM0")
        stringBuilder = java.lang.StringBuilder()

       CoroutineScope(Dispatchers.IO).launch {
           delay(3000)
           readScan()
       }
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
                        composable(Screen.InsertIdScreen.route) {
                            viewModel!!.initInsertId()
                            InsertIdScreen(navController,viewModel!!)
                        }
                        composable(Screen.InsertOtpScreen.route) {
                            viewModel!!.initInsertOtp()
                            val resultClass = navController.previousBackStackEntry?.savedStateHandle?.get<ResultClass?>(
                                Constants.STATE_RESULT_CLASS)
                            InsertOtpScreen(navController,viewModel!!,resultClass)
                        }
                    }
                }
            }
        }
    }

    private fun readScan(){
        var context = this
        CoroutineScope(Dispatchers.IO).launch {
            while (flag){
                delay(500)
                if (inputStream != null) {
                    var size = 0
                    val buffer = ByteArray(2000)
                    try {
                        size = inputStream!!.available()
                        if (size > 0) {
                            size = inputStream!!.read(buffer)
                            Log.e("TAG", "result1:$size")
                            if (size > 0) {
                               stringBuilder?.append(
                                    String(
                                        buffer,
                                        0,
                                        size,
                                        Charset.defaultCharset()
                                    )
                                )
                                withContext(Dispatchers.Main) {
                                    if (stringBuilder.toString().isNotEmpty()) {
                                        Toast.makeText(context,stringBuilder,Toast.LENGTH_LONG).show()
                                        viewModel?.setBarcode(stringBuilder.toString())
                                       // textView.setText(stringBuilder.toString())
                                        Log.e(
                                            "TAG",
                                            "result:" + stringBuilder.toString()
                                        )
                                    }
                                }
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Log.e("TAG", "error:$e")
                    }
                }
            }
        }
    }



    override fun onPause() {
        super.onPause()
        flag = false
    }

    override fun onResume() {
        super.onResume()
        flag = true
    }

    override fun onStop() {
        super.onStop()
        try {
            if (inputStream != null) {
                inputStream!!.close()
                inputStream = null
            }
            if (serial != null) {
                serial!!.close()
                serial = null
            }
            if (stringBuilder != null) {
                stringBuilder = null
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }



}

