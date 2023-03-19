package com.gicproject.sohpreadqrcode.domain.repository

import com.gicproject.sohpreadqrcode.data.remote.dto.*
import com.gicproject.sohpreadqrcode.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MyRepository {

    suspend fun checkQrCode(appId: String): List<ResultClass>?


}