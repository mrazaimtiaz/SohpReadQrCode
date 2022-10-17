package com.gicproject.salamattendanceapp.presentation

import com.gicproject.emojisurveyapp.domain.model.ResultId
import com.gicproject.salamattendanceapp.domain.model.ResultClass

data class UserInputScreenState(
    val isLoading: Boolean = false,
    val success: String = "",
    val resultId: ResultClass = ResultClass(),
    val error: String = ""
)
