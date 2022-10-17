package com.gicproject.salamattendanceapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("employeeNumber") var employeeNumber: String? = null,
    @SerializedName("fullNameEn") var fullNameEn: String? = null,
    @SerializedName("fullNameAr") var fullNameAr: String? = null,
    @SerializedName("mobileNo") var mobileNo: String? = null,
    @SerializedName("departmentEn") var departmentEn: String? = null,
    @SerializedName("departmentAr") var departmentAr: String? = null,
    @SerializedName("iD") var iD: Int? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("image") var image: String? = null,

): java.io.Serializable