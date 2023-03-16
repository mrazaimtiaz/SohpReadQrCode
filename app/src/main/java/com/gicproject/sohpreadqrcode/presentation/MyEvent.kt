package com.gicproject.sohpreadqrcode.presentation

import android.content.Context


sealed class MyEvent {
    data class CheckQrCode(val barcode: String): MyEvent()
    data class GetAttendance(val isCheckIn: Boolean,val id: String,val context: Context): MyEvent()
    data class SendOtpCode(val employeeCode: String): MyEvent()
    data class EmployeeInfoCode(val employeeId: String): MyEvent()
}

