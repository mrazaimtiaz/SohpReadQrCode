package com.gicproject.sohpreadqrcode.domain.repository

import com.gicproject.sohpreadqrcode.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MyRepository {

    suspend fun checkQrCode(appId: String): List<ResultClass>?


    suspend fun getPatientInfo(appId: String): List<PatientInfo>?


}