package com.gicproject.salamattendanceapp.domain.model

import com.google.gson.annotations.SerializedName


data class CheckOtpSend (
    @SerializedName("DeviceID"  ) var deviceID  : String? = "",
    @SerializedName("EmployeeCode"    ) var employeeCode    : String? = "",
    @SerializedName("secretKey" ) var secretKey : String? = ""
)
