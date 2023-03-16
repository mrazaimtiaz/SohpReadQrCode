package com.gicproject.sohpreadqrcode.presentation

import com.gicproject.sohpreadqrcode.domain.model.ResultClass

data class InsertIdScreenState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = "",
    val resultClass: ResultClass = ResultClass(),
)
