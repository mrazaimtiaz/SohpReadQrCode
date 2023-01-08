package com.gicproject.salamattendanceapp.domain.repository

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.salamattendanceapp.data.remote.dto.*
import com.gicproject.salamattendanceapp.domain.model.CheckOtpSend
import com.gicproject.salamattendanceapp.domain.model.CheckQrCodeSend
import com.gicproject.salamattendanceapp.domain.model.CheckSend
import com.gicproject.salamattendanceapp.domain.model.ResultClass
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyRepository {

    suspend fun sendOtp(
        checkOtpSend: CheckOtpSend
    ): List<ResultClass>?

    suspend fun checkQrCode(checkQrCodeSend: CheckQrCodeSend): List<ResultClass>?

    suspend fun checkSend(checkSend: CheckSend, isCheckIn: Boolean): List<ResultClass>?

    suspend fun getLocations(): List<LocationDto>?

    suspend fun getClinics(id: String): List<ClinicDto>?

    suspend fun getQuestions(): List<QuestionDto>?

    suspend fun getAnswers(id: String): List<AnswerDto>?

    suspend fun submitAnswer(
        questionId: String,
        answerId: String,
        value: String,
        mobileNumber: String,
        deviceId: String,
        locationId: String,
        sendEmail: Boolean,
        clinicId: String,
        clinicName: String
    ): List<ResultIdDto>?

    fun getCustomerSupport(): Flow<List<CustomerInput>>

    suspend fun getCustomerInputById(customerInput: Int): CustomerInput?

    suspend fun insertCustomerSupport(customerInput: CustomerInput): Long?

    suspend fun deleteCustomer(customerInput: CustomerInput)


}