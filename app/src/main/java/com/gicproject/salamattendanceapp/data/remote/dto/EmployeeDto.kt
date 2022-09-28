package com.gicproject.salamattendanceapp.data.remote.dto

import com.gicproject.emojisurveyapp.domain.model.Question
import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Name") var Name: String? = null,
    @SerializedName("Text_En") var designation: String? = null,
    @SerializedName("Text_Ar") var image: String? = null,

): java.io.Serializable {
    fun toQuestion(): Question {
        return Question(
            ID = ID,
            TextEn = designation,
            TextAr = image,
        )
    }
}