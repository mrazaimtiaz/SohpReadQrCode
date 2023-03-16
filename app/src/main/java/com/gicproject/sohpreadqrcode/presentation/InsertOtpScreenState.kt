package com.gicproject.sohpreadqrcode.presentation

import com.gicproject.sohpreadqrcode.data.remote.dto.EmployeeDto

data class InsertOtpScreenState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = "",
    val employeeDto: EmployeeDto = EmployeeDto(),
)
