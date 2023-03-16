package com.gicproject.sohpreadqrcode.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("EmployeeNumber") var employeeNumber: String? = null,
    @SerializedName("FullNameEn") var fullNameEn: String? = null,
    @SerializedName("FullNameAr") var fullNameAr: String? = null,
    @SerializedName("MobileNo") var mobileNo: String? = null,
    @SerializedName("DepartmentEn") var departmentEn: String? = null,
    @SerializedName("DepartmentAr") var departmentAr: String? = null,
    @SerializedName("ID") var iD: Int? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("Message") var message: String? = null,
    @SerializedName("image") var image: String? = null,


): java.io.Serializable
