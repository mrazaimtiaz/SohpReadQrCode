package com.gicproject.salamattendanceapp.data.remote.dto

import com.gicproject.emojisurveyapp.domain.model.Question
import com.google.gson.annotations.SerializedName

data class QuestionDto(
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Name") var Name: String? = null,
    @SerializedName("Text_En") var TextEn: String? = null,
    @SerializedName("Text_Ar") var TextAr: String? = null,
    @SerializedName("showInfoPage") var showInfoPage: Boolean? = null

) {
    fun toQuestion(): Question {
        return Question(
            ID = ID,
            TextEn = TextEn,
            TextAr = TextAr,
            showInfoPage = showInfoPage,
        )
    }
}