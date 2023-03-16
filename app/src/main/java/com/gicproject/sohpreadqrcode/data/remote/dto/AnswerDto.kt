package com.gicproject.sohpreadqrcode.data.remote.dto

import com.gicproject.emojisurveyapp.domain.model.Answer
import com.google.gson.annotations.SerializedName

data class AnswerDto(
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Text_En") var TextEn: String? = null,
    @SerializedName("Text_Ar") var TextAr: String? = null,
    @SerializedName("Value") var Value: Int? = null,
    @SerializedName("Question_ID") var QuestionID: Int? = null,
    @SerializedName("AnswerLogo") var AnswerLogo: String? = null,
    @SerializedName("Color") var Color: String? = null,
    @SerializedName("ShowInfoPage")  var showInfoPage: Boolean? = null,
    @SerializedName("SendEmail") var SendEmail: Boolean? = null,
){
    fun toAnswer(): Answer {
        return Answer(
            ID = ID,
            TextEn = TextEn,
            TextAr = TextAr,
            Value = Value,
            QuestionID = QuestionID,
            AnswerLogo = AnswerLogo,
            Color = Color,
            showInfoPage = showInfoPage,
            SendEmail = SendEmail
        )
    }
}