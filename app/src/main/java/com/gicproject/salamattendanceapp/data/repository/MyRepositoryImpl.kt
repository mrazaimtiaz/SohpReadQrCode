package com.gicproject.salamattendanceapp.data.repository

import com.gicproject.salamattendanceapp.data.remote.dto.*


import com.gicproject.salamattendanceapp.data.remote.MyApi
import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.salamattendanceapp.data.data_source.MyDao
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val api: MyApi, private val dao: MyDao
): MyRepository {

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