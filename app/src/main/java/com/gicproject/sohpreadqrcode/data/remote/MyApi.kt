package com.gicproject.sohpreadqrcode.data.remote

import com.gicproject.sohpreadqrcode.domain.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("api/scanapp")
    suspend fun checkQrCode(
        @Query("appid") appid: String
    ): List<ResultClass>?

    @GET("api/GetAppointmentID")
    suspend fun GetAppointmentID(
        @Query("id") appid: String
    ): List<PatientInfo>?

}