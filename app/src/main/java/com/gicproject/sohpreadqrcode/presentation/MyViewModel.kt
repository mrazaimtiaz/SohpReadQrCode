package com.gicproject.sohpreadqrcode.presentation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.common.Constants.Companion.KEY_DEVICE_ID
import com.gicproject.sohpreadqrcode.common.Resource
import com.gicproject.sohpreadqrcode.domain.model.CheckOtpSend
import com.gicproject.sohpreadqrcode.domain.model.CheckPersonalInfoSend
import com.gicproject.sohpreadqrcode.domain.model.CheckQrCodeSend
import com.gicproject.sohpreadqrcode.domain.repository.DataStoreRepository
import com.gicproject.sohpreadqrcode.domain.use_case.MyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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

    private val _resetScreens = mutableStateOf(false)
    val resetScreens: State<Boolean> = _resetScreens

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    private val _stateInsertOtp = mutableStateOf(InsertOtpScreenState())
    val stateInsertOtp: State<InsertOtpScreenState> = _stateInsertOtp

    private val _stateInsertId = mutableStateOf(InsertIdScreenState())
    val stateInsertId: State<InsertIdScreenState> = _stateInsertId

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

    fun initInsertId(){
        _stateInsertId.value = InsertIdScreenState()
    }

    fun initInsertOtp(){
        _stateInsertOtp.value = InsertOtpScreenState()
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

    fun showUserInputScreen(barcode: String) {
        Log.d(TAG, "showUserInputScreen barcode: $barcode")
        onEvent(MyEvent.CheckQrCode(barcode = barcode))

    }

    fun showEmployeeInfoScreen(isCheckIn: Boolean,context: Context,employeeId: String) {
        //_photoUri
        val iStream: InputStream? = _photoUri.value?.let {
            context.contentResolver.openInputStream(
                it
            )
        }
        val inputData: ByteArray? = iStream?.let { getBytes(it) }
        viewModelScope.launch {
            onEvent(MyEvent.GetAttendance(isCheckIn = isCheckIn,id = employeeId,context))
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
        
        Log.d("TAG", "onEvent: called unsaved")
        when (event) {
            is MyEvent.EmployeeInfoCode -> {
                Log.d("TAG", "onEvent: employeeId ${event.employeeId} ")
                viewModelScope.launch {
                    val deviceId = repository.getString(KEY_DEVICE_ID) ?: ""


                    myUseCases.getEmployeeInfoCode(
                        checkPersonalInfoSend = CheckPersonalInfoSend(deviceID = deviceId, employeeNumber = event.employeeId, secretKey = Constants.SECRETKEY)
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let {
                                    Log.d(TAG, "onEvent: getCheckQrCode success")
                                    viewModelScope.launch {
                                        _stateInsertOtp.value = _stateInsertOtp.value.copy(isLoading = false, success = "success", employeeDto = it)
                                    }
                                }
                            }
                            is Resource.Error -> {
                                Log.d(TAG, "onEvent: getCheckQrCode failure")
                                _stateInsertOtp.value = _stateInsertOtp.value.copy(
                                    isLoading = false,
                                    error = result.message ?: "An unexpected error occurred",
                                )
                            }
                            is Resource.Loading -> {
                                _stateInsertOtp.value = _stateInsertOtp.value.copy(isLoading = true)
                            }
                            else -> {}
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is MyEvent.SendOtpCode -> {
                Log.d("TAG", "onEvent: called unsaved1 ")
                Log.d("TAG", "onEvent: called unsaved2 ${event.employeeCode} ")
                viewModelScope.launch {
                    val deviceId = repository.getString(KEY_DEVICE_ID) ?: ""
                    myUseCases.getSendOtpCode(
                        checkOtpSend = CheckOtpSend(deviceID = deviceId, employeeCode = event.employeeCode, secretKey = Constants.SECRETKEY)
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let {
                                    Log.d(TAG, "onEvent: getCheckQrCode success")
                                    viewModelScope.launch {
                                        try{
                                            it.ID = event.employeeCode.toInt()
                                            _stateInsertId.value = _stateInsertId.value.copy(isLoading = false, success = "success", resultClass = it)
                                        }catch (e: java.lang.Exception){
                                            _stateInsertId.value = _stateInsertId.value.copy(
                                                isLoading = false,
                                                error = "Employee ID Should be Number (Integer)",
                                            )
                                        }

                                    }
                                }
                            }
                            is Resource.Error -> {
                                Log.d(TAG, "onEvent: getCheckQrCode failure")
                                _stateInsertId.value = _stateInsertId.value.copy(
                                    isLoading = false,
                                    error = result.message ?: "An unexpected error occurred",
                                )
                            }
                            is Resource.Loading -> {
                                _stateInsertId.value = _stateInsertId.value.copy(isLoading = true)
                            }
                            else -> {}
                        }
                    }.launchIn(viewModelScope)


                }
            }
            is MyEvent.CheckQrCode -> {
                Log.d("TAG", "onEvent: called unsaved1 ")
                Log.d("TAG", "onEvent: called unsaved2 ${event.barcode} ")
                viewModelScope.launch {
                    val deviceId = repository.getString(KEY_DEVICE_ID) ?: ""
                    myUseCases.getCheckQrCode(
                        checkQrCodeSend = CheckQrCodeSend(deviceID = deviceId, QRCode = event.barcode, secretKey = Constants.SECRETKEY)
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let {
                                    Log.d(TAG, "onEvent: getCheckQrCode success")
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
            is MyEvent.GetAttendance -> {

            }
        }


    }
}
