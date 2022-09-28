package com.gicproject.salamattendanceapp.presentation

import com.gicproject.emojisurveyapp.domain.model.ResultId

data class UserInputScreenState(
    val isLoading: Boolean = false,
    val success: String = "",
    val resultId: ResultId = ResultId(),
    val error: String = ""
)
