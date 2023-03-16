package com.gicproject.sohpreadqrcode.data.repository


import android.util.ArrayMap
import android.util.Log
import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.sohpreadqrcode.data.data_source.MyDao
import com.gicproject.sohpreadqrcode.data.remote.MyApi
import com.gicproject.sohpreadqrcode.data.remote.dto.*
import com.gicproject.sohpreadqrcode.domain.model.*
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject


private const val TAG = "MyRepositoryImpl"
class MyRepositoryImpl @Inject constructor(
    private val api: MyApi, private val dao: MyDao
): MyRepository {

    override suspend fun sendOtp(checkOtpSend: CheckOtpSend): List<ResultClass>? {
        return api.sendOtp(checkOtpSend)
    }

    override suspend fun checkQrCode(checkQrCodeSend: CheckQrCodeSend): List<ResultClass>? {
        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
        jsonParams["QRCode"] = checkQrCodeSend.QRCode?.trim()?.replace("\u0000","")
        jsonParams["deviceID"] = checkQrCodeSend.deviceID
        jsonParams["secretKey"] = checkQrCodeSend.secretKey

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        Log.d(TAG, "checkQrCode: ${JSONObject(jsonParams)}")
        return api.checkQrCode(body)
      //  return api.checkQrCode(checkQrCodeSend)
    }

    override suspend fun personalInfo(checkPersonalInfoSend: CheckPersonalInfoSend): List<EmployeeDto>? {
      return  api.personalInfoNew(checkPersonalInfoSend)
    }

    override suspend fun checkSend(checkSend: CheckSend,isCheckIn: Boolean): List<ResultClass>? {
        return if(isCheckIn){
            api.checkIn(checkSend =checkSend )
        }else{
            api.checkOut(checkSend =checkSend )
        }
    }

    override suspend fun getLocations(): List<LocationDto>? {
        return api.getLocations()
    }

    override suspend fun getClinics(id: String): List<ClinicDto>? {
        return api.getClinics(id)
    }

    override suspend fun getQuestions(): List<QuestionDto>? {
        return api.getQuestions()
    }

    override suspend fun getAnswers(id: String): List<AnswerDto>? {
        return api.getAnswers(id)
    }

    override suspend fun submitAnswer(
        questionId: String,
        answerId: String,
        value: String,
        mobileNumber: String,
        deviceId: String,
        locationId: String,
        sendEmail: Boolean,
        clinicId: String,
        clinicName: String
    ): List<ResultIdDto>? {
        return api.submitAnswer(
            questionID = questionId,
            answerID = answerId,
            value = value,
            mobileNumber = mobileNumber,
            deviceID = deviceId,
            locationID = locationId,
            sendEmail = sendEmail,
            clinicID = clinicId,
            clinicName = clinicName
        )
    }

    override fun getCustomerSupport(): Flow<List<CustomerInput>> {
       return dao.getCustomerSupport()
    }

    override suspend fun getCustomerInputById(customerInput: Int): CustomerInput? {
        return dao.getCustomerInputById(customerInput)
    }

    override suspend fun insertCustomerSupport(customerInput: CustomerInput): Long? {
        return dao.insertCustomerSupport(customerInput)
    }

    override suspend fun deleteCustomer(customerInput: CustomerInput) {
        return dao.deleteCustomer(customerInput)
    }
}