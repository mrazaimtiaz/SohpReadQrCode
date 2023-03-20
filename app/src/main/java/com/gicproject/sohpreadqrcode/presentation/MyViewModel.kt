package com.gicproject.sohpreadqrcode.presentation

import android.content.Context
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.ToneGenerator
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.common.Constants.Companion.KEY_DEVICE_ID
import com.gicproject.sohpreadqrcode.common.Resource
import com.gicproject.sohpreadqrcode.domain.repository.DataStoreRepository
import com.gicproject.sohpreadqrcode.domain.use_case.MyUseCases
import com.telpo.tps550.api.printer.UsbThermalPrinter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.inject.Inject


private const val TAG = "MyViewModel"
@HiltViewModel
class MyViewModel @Inject constructor(
    private val myUseCases: MyUseCases,
    val repository: DataStoreRepository
) : ViewModel() {


    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme


    private val _stateMain = mutableStateOf(MainScreenState())
    val stateMain: State<MainScreenState> = _stateMain


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
        initPreference()
        //   blueLamps()
        /*viewModelScope.launch {
            delay(500)
            offLamps()
        }*/
    }

    fun setBarcode( barcode: String){
        _stateMain.value = _stateMain.value.copy(error = barcode)

    }



    fun emptyToast(){
        _stateMain.value = _stateMain.value.copy(error = "")
    }

    fun initGetMain() {
        _stateMain.value = MainScreenState()
    }


    var streamType = AudioManager.STREAM_DTMF
    var toneGenerator: ToneGenerator? = ToneGenerator(streamType, 100)
    fun beep() {
        val toneType = ToneGenerator.TONE_PROP_BEEP
        toneGenerator?.startTone(toneType)
    }

    fun initUserInput() {
        Log.d("TAG", "initUserInput: called")
        _shouldShowCamera.value = true
        _shouldShowPhoto.value = false
        //_stateUserInput.value = UserInputScreenState()
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

    fun checkQrCode(appId: String) {
        Log.d(TAG, "showUserInputScreen appId: $appId")
        onEvent(MyEvent.CheckQrCode(appId = appId))

    }

    fun setErrorMessageToMainScreen(message: String){
        _stateMain.value = _stateMain.value.copy(
            isLoading = false,
            error = message,
        )

    }




    private var mPrinter: UsbThermalPrinter? = null
    private var mContext: Context? = null

    fun initPrinter(printer: UsbThermalPrinter?, context: Context){
        mPrinter = printer
        mContext = context
    }

    fun  funcPrinterConnect(name: String){
        CoroutineScope(Dispatchers.IO).launch {


            try {
                mPrinter?.reset()
                mPrinter?.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE)

                mPrinter?.setGray(5)
                //  mPrinter?.setLineSpace(5)
                /*    val bitmap = CreateCode(
                        name,
                        BarcodeFormat.CODE_128,
                        320,
                        176
                    )*/
                /*   if (bitmap != null) {
                       mPrinter?.printLogo(bitmap, true)
                   }*/
                //  mPrinter?.setGray(5)



                mPrinter?.setBold(true)

                mPrinter?.setTextSize(25)
                mPrinter?.addString(
                    """${name} """.trimIndent()
                )

                mPrinter?.printString()
                mPrinter?.walkPaper(20)

                mPrinter?.reset()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
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

    fun myEncodeImage( bm: Bitmap): String{
        var  baos : ByteArrayOutputStream=  ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        var b = baos.toByteArray();
        val encImage: String = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);

        return encImage;
    }
    fun onEvent(event: MyEvent) {

        when (event) {
            is MyEvent.CheckQrCode -> {
                Log.d("TAG", "onEvent: called unsaved2 ${event.appId} ")
                viewModelScope.launch {
                    val deviceId = repository.getString(KEY_DEVICE_ID) ?: ""
                    myUseCases.getCheckQrCode(
                        appId = event.appId
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let {
                                    Log.d(TAG, "onEvent: getCheckQrCode success")
                                    viewModelScope.launch {
                                        delay(100)
                                        _stateMain.value = _stateMain.value.copy(isLoading = false, success = it.Message ?: "Empty Message",)

                                    }
                                }
                            }
                            is Resource.Error -> {
                                Log.d(TAG, "onEvent: getCheckQrCode failure")
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

        }


    }
}
