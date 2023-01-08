package com.gicproject.salamattendanceapp.presentation

import com.gicproject.salamattendanceapp.data.remote.dto.EmployeeDto
import com.gicproject.salamattendanceapp.domain.model.ResultClass

data class InsertOtpScreenState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = "",
)
