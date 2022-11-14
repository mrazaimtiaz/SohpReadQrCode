package com.gicproject.salamattendanceapp.presentation

import android.content.Context
import com.gicproject.emojisurveyapp.domain.model.Answer
import com.gicproject.emojisurveyapp.domain.model.Clinic
import com.gicproject.emojisurveyapp.domain.model.CustomerInput


sealed class MyEvent {
    data class CheckQrCode(val barcode: String): MyEvent()
    data class GetAttendance(val isCheckIn: Boolean,val id: String,val context: Context): MyEvent()
}

