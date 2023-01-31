package com.gicproject.salamattendanceapp.data.remote

import com.gicproject.salamattendanceapp.data.remote.dto.*
import com.gicproject.salamattendanceapp.domain.model.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MyApi {

    @POST("api/sendotp")
    suspend fun sendOtp(
        @Body checkOtpSend: CheckOtpSend
    ): List<ResultClass>?

    @POST("api/PersonalInfoNew")
    suspend fun personalInfoNew(
        @Body checkPersonalInfoSend: CheckPersonalInfoSend
    ): List<EmployeeDto>?



    @POST("api/CheckQrCode")
    suspend fun checkQrCode(
        @Body checkQrCodeSendModel: RequestBody
    ): List<ResultClass>?

    @POST("api/CheckIn")
    suspend fun checkIn(
        @Body checkSend: CheckSend
    ): List<ResultClass>?

    @POST("api/CheckOut")
    suspend fun checkOut(
        @Body checkSend: CheckSend
    ): List<ResultClass>?

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