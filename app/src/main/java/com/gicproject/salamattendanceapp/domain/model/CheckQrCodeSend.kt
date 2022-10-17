package com.gicproject.salamattendanceapp.domain.model

import com.google.gson.annotations.SerializedName


data class CheckQrCodeSend (
    @SerializedName("deviceID"  ) var deviceID  : String? = null,
    @SerializedName("QRCode"    ) var QRCode    : String? = null,
    @SerializedName("secretKey" ) var secretKey : String? = null
)