package com.gicproject.sohpreadqrcode.presentation

import com.gicproject.sohpreadqrcode.domain.model.PatientInfo

data class MainScreenState(
    val isLoading: Boolean = false,
    val isLoadingPagination: Boolean = false,
    val success: String = "",
    val patientInfo: PatientInfo? = PatientInfo(),
    val error: String = ""
)
