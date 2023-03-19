package com.gicproject.sohpreadqrcode

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.*
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.presentation.MainScreen
import com.gicproject.sohpreadqrcode.presentation.MessageInfoScreen
import com.gicproject.sohpreadqrcode.presentation.MyViewModel
import com.gicproject.sohpreadqrcode.presentation.SettingScreen
import com.gicproject.sohpreadqrcode.ui.theme.ClassRoomAttendanceTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.telpo.tps550.api.TelpoException
import com.telpo.tps550.api.printer.UsbThermalPrinter
import com.telpo.tps550.api.serial.Serial
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val barcode = StringBuffer()

    private var viewModel: MyViewModel? = null

    private var stringBuilder: StringBuilder? = null

    private var serial: Serial? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    var flag = true



    var mUsbThermalPrinter = UsbThermalPrinter(this@MainActivity)

    val PRT_TAG = "com.print.service.printservice"
  /*  //working barcode
    @SuppressLint("RestrictedApi")
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event?.action == KeyEvent.ACTION_DOWN) {
            val pressedKey = event.unicodeChar.toChar()
            barcode.append(pressedKey)
        }
        if (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {

            viewModel?.checkQrCode(barcode.toString())
            barcode.delete(0, barcode.length)
        }
        return super.dispatchKeyEvent(event)
    }*/

    private fun initSerial(serialpath: String) {
        try {
            serial = Serial(serialpath, 9600, 0) //ttyHSL1
            inputStream = serial?.inputStream
            outputStream = serial?.outputStream
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private var mUsbBroadCastReceiver: UsbBroadCastReceiver? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(PRT_TAG)
        intent.setPackage(packageName)
        intent.component = ComponentName("com.print.service", "com.print.service.printservice")
       // setupPrinter()

        initSerial("/dev/ttyACM0")
        stringBuilder = java.lang.StringBuilder()






       CoroutineScope(Dispatchers.IO).launch {
           delay(3000)
           readScan()
       }
        setContent {
            viewModel = hiltViewModel()

            if (mUsbBroadCastReceiver == null) {
                val intentFilter = IntentFilter()
                intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
                intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
                mUsbBroadCastReceiver = UsbBroadCastReceiver()
                registerReceiver(mUsbBroadCastReceiver, intentFilter)
            //    viewModel?.initPrinter(mUsbThermalPrinter, this@MainActivity)

            }

            val darkTheme = viewModel!!.isDarkTheme.value
            val systemUiController = rememberSystemUiController()

            //test
          /*  LaunchedEffect(Unit) {
                while(true){

                    delay(1000L)
                    checkCode("E652AED2-3EF5-40C1-8244-884FB070C9B4-----2023-03-19 11:48:55")
                }

            }*/

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
                            route = Screen.MessageInfoScreen.route
                        ) {
                            val message = navController.previousBackStackEntry?.savedStateHandle?.get<String?>(
                                Constants.STATE_MSG)

                            val isSuccess = navController.previousBackStackEntry?.savedStateHandle?.get<Boolean?>(
                                Constants.STATE_IS_SUCCESS)
                            MessageInfoScreen(
                                navController,
                                viewModel!!,
                                isSuccess,
                                message
                            )
                        }



                        composable(Screen.SettingScreen.route) {
                            SettingScreen(navController,viewModel!!)
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
                                     //   Toast.makeText(context,stringBuilder,Toast.LENGTH_LONG).show()
                                    checkCode(stringBuilder.toString())
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

    var time =    System.currentTimeMillis()
    fun checkCode(qrCode: String){
        var currentTimeMills = System.currentTimeMillis()

        if((currentTimeMills - time) > 2000){
            Log.d("TAG", "checkCodetimemills: ${currentTimeMills - time}")
            time =  System.currentTimeMillis()
            var list =   qrCode.split("-----")
            if(list.size > 1){

                Log.d("TAG", "readScan: ${list[0]}   ${list[1]}")
                var time = list[1]


                //current date format
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

                val barcodeDate = dateFormat.parse(time)
                val currentTime = Calendar.getInstance().time
                val diffInMs: Long = currentTime.time - barcodeDate.time

                val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(diffInMs)


              //  if(diffInSec > 65){
                 //   Log.d("TAG", "onCreate:diif sec $diffInSec")
               //     viewModel?.setErrorMessageToMainScreen("Qr Code is Expired! Please Generate Again and try again")
            //    }else{
                    Log.d("TAG", "onCreate:diif sec1 $diffInSec")
                    viewModel?.checkQrCode(list[0])
              //  }

            }
        }

    }



    override fun onPause() {
        super.onPause()
       // flag = false
    }

    override fun onResume() {
       // recreate()
        super.onResume()
        flag = true
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(printReceive)
       mUsbThermalPrinter.stop()
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

    var dialog: ProgressDialog? = null
    fun setupPrinter() {
        // TODO Auto-generated method stub
        super.onStart()
        val pIntentFilter = IntentFilter()
        pIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        pIntentFilter.addAction("android.intent.action.BATTERY_CAPACITY_EVENT")
        registerReceiver(printReceive, pIntentFilter)
        dialog = ProgressDialog(this@MainActivity)
        dialog?.setTitle("ICard")
        dialog?.setMessage("Waiting")
        dialog?.setCancelable(false)
        dialog?.show()

        var printVersion: String? = null
        Thread {
            try {
                //mUsbThermalPrinter.start(0);
                mUsbThermalPrinter.start(1)
                mUsbThermalPrinter.reset()
                printVersion = mUsbThermalPrinter.version

                viewModel?.initPrinter(mUsbThermalPrinter, this@MainActivity)
            } catch (e: TelpoException) {
                e.printStackTrace()
            } finally {
                val PRINTVERSION = 5
                if (printVersion != null) {
                    CoroutineScope(Dispatchers.Main).launch {

                        dialog!!.dismiss()
                        Toast.makeText(
                            this@MainActivity,
                            "${PRINTVERSION.toString()} 1",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    dialog!!.dismiss()
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            this@MainActivity,
                            "Operation print fail",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }.start()
    }


    private val printReceive: BroadcastReceiver = object : BroadcastReceiver() {
        var LowBattery = false
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Intent.ACTION_BATTERY_CHANGED) {
                val status = intent.getIntExtra(
                    BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING
                )
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0)
                if (status != BatteryManager.BATTERY_STATUS_CHARGING) {
                    LowBattery = level * 5 <= scale
                } else {
                    LowBattery = false
                }
            } else if (action == "android.intent.action.BATTERY_CAPACITY_EVENT") {
                val status = intent.getIntExtra("action", 0)
                val level = intent.getIntExtra("level", 0)
                if (status == 0) {
                    LowBattery = level < 1
                } else {
                    LowBattery = false
                }
            }
        }
    }



}


class UsbBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val device = intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice?
        /*    when (intent.action) {
                UsbManager.ACTION_USB_DEVICE_ATTACHED ->                     // 当USB设备连接到USB总线时，在主机模式下发送此意图。
                    onToast("plug-in device vid:" + device!!.vendorId + "  pid:" + device.productId)
                UsbManager.ACTION_USB_DEVICE_DETACHED ->                     // 当USB设备在主机模式下脱离USB总线时发送此意图。
                //    onToast("Pull out device vid:" + device!!.vendorId + "  pid:" + device.productId)
            }*/
    }
}


