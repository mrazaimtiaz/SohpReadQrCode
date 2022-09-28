package com.gicproject.salamattendanceapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.util.Log
import android_serialport_api.SerialPort
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gicproject.salamattendanceapp.common.Constants.Companion.KEY_DEVICE_ID
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.domain.repository.DataStoreRepository
import com.gicproject.salamattendanceapp.domain.use_case.MyUseCases
import com.gicproject.salamattendanceapp.led.LampsUtil
import com.gicproject.salamattendanceapp.led.UartSend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCases: MyUseCases,
    val repository: DataStoreRepository
) : ViewModel() {

    private val _resetScreens = mutableStateOf(false)
    val resetScreens: State<Boolean> = _resetScreens

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    private val _stateMain = mutableStateOf(MainScreenState())
    val stateMain: State<MainScreenState> = _stateMain

    private val _stateUserInput = mutableStateOf(UserInputScreenState())
    val stateUserInput: State<UserInputScreenState> = _stateUserInput

    private val _isCameraInitialized = mutableStateOf(false)
    val isCameraInitialized: State<Boolean> = _isCameraInitialized

    private val _shouldShowCamera = mutableStateOf(false)
    val shouldShowCamera: State<Boolean> = _shouldShowCamera

    private val _shouldShowPhoto = mutableStateOf(false)
    val shouldShowPhoto: State<Boolean> = _shouldShowPhoto

    private val _photoUri = mutableStateOf(null as Uri?)
    val photoUri: State<Uri?> = _photoUri

    private var job: Job? = null

    init {
        var lampUtil: LampsUtil = LampsUtil()
        initPreference()
        //   blueLamps()
        /*viewModelScope.launch {
            delay(500)
            offLamps()
        }*/
    }

    fun initGetMain() {
        _stateMain.value = MainScreenState()
    }

    fun initUserInput() {
        Log.d("TAG", "initUserInput: called")
        _shouldShowCamera.value = true
        _shouldShowPhoto.value = false
        _stateUserInput.value = UserInputScreenState()
    }

    private fun initPreference() {
        viewModelScope.launch {
            initDeviceIdPreference()
        }
    }

    private fun initDeviceIdPreference() {
        viewModelScope.launch {
            val deviceId = repository.getString(KEY_DEVICE_ID)
            if (deviceId == null) {
                val uniqueID = UUID.randomUUID().toString()
                repository.putString(KEY_DEVICE_ID, uniqueID)
            }
        }
    }

    fun setCameraInitialized(value: Boolean) {
        _isCameraInitialized.value = value
    }

    fun setPhotoUri(value: Uri) {
        _photoUri.value = value
    }

    fun setShowCamera(value: Boolean) {
        _shouldShowCamera.value = value
    }

    fun setShowPhoto(value: Boolean) {
        _shouldShowPhoto.value = value
    }

    fun showUserInputScreen(barcode: String) {
        onEvent(MyEvent.CheckQrCode(barcode = barcode))

    }

    fun showEmployeeInfoScreen(isCheckIn: Boolean,context: Context) {
        //_photoUri
        val iStream: InputStream? = _photoUri.value?.let {
            context.contentResolver.openInputStream(
                it
            )
        }
        val inputData: ByteArray? = iStream?.let { getBytes(it) }
        viewModelScope.launch {
            onEvent(MyEvent.GetAttendance(isCheckIn = isCheckIn))
        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    fun onEvent(event: MyEvent) {
        Log.d("TAG", "onEvent: called unsaved")
        when (event) {
            is MyEvent.CheckQrCode -> {
                Log.d("TAG", "onEvent: called unsaved1 ")
                Log.d("TAG", "onEvent: called unsaved2 ${event.barcode} ")
                Log.d("TAG", "onEvent: called unsaved3 ${event.barcode} ")
                viewModelScope.launch {
                    val deviceId = repository.getString(KEY_DEVICE_ID) ?: ""
                    myUseCases.getCheckQrCode(
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let {
                                    _resetScreens.value = true
                                    viewModelScope.launch {
                                        delay(100)
                                        _resetScreens.value = false

                                        _stateMain.value = _stateMain.value.copy(isLoading = false, success = "success", employeeInfo = it)

                                        // _stateMain.value = MainScreenState(success = "success")
                                    }
                                }
                            }
                            is Resource.Error -> {
                                _stateMain.value = _stateMain.value.copy(
                                    isLoading = false,
                                    error = result.message ?: "An unexpected error occurred",
                                )
                            }
                            is Resource.Loading -> {
                                _stateMain.value = _stateMain.value.copy(isLoading = true)
                            }
                            else -> {}
                        }
                    }.launchIn(viewModelScope)


                }
            }
            is MyEvent.GetAttendance -> {
                viewModelScope.launch {
                    val deviceId = repository.getString(KEY_DEVICE_ID) ?: ""
                    myUseCases.getAttendance(
                    event.isCheckIn).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                viewModelScope.launch {
                                    delay(2000)
                                    result.data?.let {
                                        _stateUserInput.value = _stateUserInput.value.copy(isLoading = false, success = "success", resultId = it)
                                    }
                                }

                            }
                            is Resource.Error -> {
                                _stateUserInput.value = _stateUserInput.value.copy(
                                    isLoading = false,
                                    error = result.message ?: "An unexpected error occurred",
                                )
                            }
                            is Resource.Loading -> {
                                _stateUserInput.value = _stateUserInput.value.copy(isLoading = true)
                            }
                            else -> {}
                        }
                    }.launchIn(viewModelScope)


                }
            }
        }

        @Throws(IOException::class)
        fun blueLamps() {

            val runnableLoop: Runnable = object : Runnable {
                @SuppressLint("SuspiciousIndentation")
                override fun run() {
                    try {
                        val ttyS1 = SerialPort(File("/dev/ttyS1"), 115200, 0)
                        UartSend.UartAllB(ttyS1, "ttyS1").run()

                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            runnableLoop.run()
        }

        @Throws(IOException::class)
        fun greenLamps() {
            val runnableLoop: Runnable = object : Runnable {
                //LOOP thread
                @SuppressLint("SuspiciousIndentation")
                override fun run() {
                    try {
                        val ttyS1 = SerialPort(File("/dev/ttyS1"), 115200, 0)
                        UartSend.UartAllG(ttyS1, "ttyS1").run()

                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            runnableLoop.run()
        }

        @Throws(IOException::class)
        fun redLamps() {
            val runnableLoop: Runnable = object : Runnable {
                //LOOP thread
                @SuppressLint("SuspiciousIndentation")
                override fun run() {
                    try {
                        var ttyS1 = SerialPort(File("/dev/ttyS1"), 115200, 0)
                        UartSend.UartAllR(ttyS1, "ttyS1").run()

                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            runnableLoop.run()
        }

        @Throws(IOException::class)
        fun yellowLamps() {
            val handlerLoop = Handler()
            val runnableLoop: Runnable = object : Runnable {
                @SuppressLint("SuspiciousIndentation")
                override fun run() {
                    try {
                        Log.d("TAG", "run: called run")
                        var ttyS1 = SerialPort(File("/dev/ttyS1"), 115200, 0)
                        UartSend.UartAllG(ttyS1, "ttyS1").run()

                    } catch (e: SecurityException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            runnableLoop.run()


        }

        fun offLamps() {
            viewModelScope.launch {
                try {
                    var ttyS1 = SerialPort(File("/dev/ttyS1"), 115200, 0)
                    UartSend.UartAllOff(ttyS1, "ttyS1").run()
                } catch (e: SecurityException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }


        }
    }
}
