package com.gicproject.salamattendanceapp.domain.model

import com.google.gson.annotations.SerializedName


data class ResultClass (
    @SerializedName("ID"         ) var ID        : Int?    = null,
    @SerializedName("status"     ) var status    : Int?    = null,
    @SerializedName("Message"    ) var Message   : String? = null,
    @SerializedName("Message_Ar" ) var MessageAr : String? = null,
    @SerializedName("EmployeeName" ) var EmployeeName : String? = null,
    @SerializedName("EmployeeCode" ) var EmployeeCode : String? = null,
)