package com.gicproject.sohpreadqrcode.presentation

import com.gicproject.sohpreadqrcode.data.remote.dto.EmployeeDto

data class MainScreenState(
    val isLoading: Boolean = false,
    val isLoadingPagination: Boolean = false,
    val success: String = "",
    val employeeInfo: EmployeeDto = EmployeeDto(),
    val error: String = ""
)
