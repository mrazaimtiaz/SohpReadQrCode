package com.gicproject.sohpreadqrcode.presentation

data class MainScreenState(
    val isLoading: Boolean = false,
    val isLoadingPagination: Boolean = false,
    val success: String = "",
    val error: String = ""
)
