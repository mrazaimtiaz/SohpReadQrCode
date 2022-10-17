package com.gicproject.salamattendanceapp.domain.model

import com.google.gson.annotations.SerializedName


data class CheckSend (

    @SerializedName("deviceID"       ) var deviceID       : String? = null,
    @SerializedName("secretKey"      ) var secretKey      : String? = null,
    @SerializedName("time"           ) var time           : String? = null,
    @SerializedName("EmployeeNumber" ) var EmployeeNumber : String? = null

)
