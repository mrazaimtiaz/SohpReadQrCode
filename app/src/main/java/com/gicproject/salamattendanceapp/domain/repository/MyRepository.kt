package com.gicproject.salamattendanceapp.domain.repository

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.salamattendanceapp.data.remote.dto.*
import kotlinx.coroutines.flow.Flow

interface MyRepository {
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