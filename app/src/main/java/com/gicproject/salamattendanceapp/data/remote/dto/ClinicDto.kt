package com.gicproject.salamattendanceapp.data.remote.dto

import com.gicproject.emojisurveyapp.domain.model.Clinic
import com.google.gson.annotations.SerializedName

data class ClinicDto(
    @SerializedName("PKID") var ID: Int? = null,
    @SerializedName("Name_En") var NameEn: String? = null,
    @SerializedName("Name_Ar") var NameAr: String? = null
){
    fun toClinic(): Clinic {
        return Clinic(
            ID = ID,
            NameEn = NameEn,
            NameAr = NameAr
        )
    }
}
