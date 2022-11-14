package com.gicproject.salamattendanceapp.domain.model

import com.google.gson.annotations.SerializedName


data class CheckQrCodeSend (
    @SerializedName("deviceID"  ) var deviceID  : String? = "",
    @SerializedName("QRCode"    ) var QRCode    : String? = "",
    @SerializedName("secretKey" ) var secretKey : String? = ""
)