package com.gicproject.sohpreadqrcode.domain.repository

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.sohpreadqrcode.data.remote.dto.*
import com.gicproject.sohpreadqrcode.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MyRepository {

    suspend fun sendOtp(
        checkOtpSend: CheckOtpSend
    ): List<ResultClass>?

    suspend fun checkQrCode(checkQrCodeSend: CheckQrCodeSend): List<ResultClass>?


    suspend fun personalInfo(checkPersonalInfoSend: CheckPersonalInfoSend): List<EmployeeDto>?

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