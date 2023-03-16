package com.gicproject.sohpreadqrcode.domain.model

import com.google.gson.annotations.SerializedName


data class CheckPersonalInfoSend (
    @SerializedName("employeeNumber"  ) var employeeNumber  : String? = "",
    @SerializedName("deviceID"    ) var deviceID    : String? = "",
    @SerializedName("secretKey" ) var secretKey : String? = ""
)
