package com.gicproject.emojisurveyapp.domain.model

import java.io.Serializable
data class Answer(
    var ID: Int? = null,
    var TextEn: String? = null,
    var TextAr: String? = null,
    var Value: Int? = null,
    var QuestionID: Int? = null,
    var AnswerLogo: String? = null,
    var showInfoPage: Boolean? = null,
    var SendEmail: Boolean? = null,
    var Color: String? = null
): Serializable