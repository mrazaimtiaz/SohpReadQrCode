package com.gicproject.sohpreadqrcode.presentation

import android.content.Context


sealed class MyEvent {
    data class CheckQrCode(val appId: String): MyEvent()
}

