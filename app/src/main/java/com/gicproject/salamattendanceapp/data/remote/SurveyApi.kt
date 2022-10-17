package com.gicproject.salamattendanceapp.data.remote

import com.gicproject.salamattendanceapp.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("api/getlocation")
    suspend fun getLocations(
    ): List<LocationDto>?

    @GET("api/getclinics")
    suspend fun getClinics(
        @Query("id")
        id: String,
    ): List<ClinicDto>?

    @GET("api/getanswers")
    suspend fun getAnswers(
        @Query("id")
        id: String,
    ): List<AnswerDto>?

    @GET("api/GetQuestions")
    suspend fun getQuestions(
    ): List<QuestionDto>?

    @GET("api/SubmitAnswer")
    suspend fun submitAnswer(
        @Query("quetionID")
        questionID: String,
        @Query("answerID")
        answerID: String,
        @Query("value")
        value: String,
        @Query("mobileNumber")
        mobileNumber: String,
        @Query("deviceID")
        deviceID: String,
        @Query("locatioID")
        locationID: String,
        @Query("sendEmail")
        sendEmail: Boolean,
        @Query("clinicID")
        clinicID: String,
        @Query("clinicName")
        clinicName: String,
    ): List<ResultIdDto>?
}