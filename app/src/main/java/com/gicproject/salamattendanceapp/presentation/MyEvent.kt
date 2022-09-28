package com.gicproject.salamattendanceapp.presentation

import com.gicproject.emojisurveyapp.domain.model.Answer
import com.gicproject.emojisurveyapp.domain.model.Clinic
import com.gicproject.emojisurveyapp.domain.model.CustomerInput


sealed class MyEvent {
    data class CheckQrCode(val barcode: String): MyEvent()
    data class GetAttendance(val isCheckIn: Boolean): MyEvent()
}

