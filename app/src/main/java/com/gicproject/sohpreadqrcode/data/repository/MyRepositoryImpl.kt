package com.gicproject.sohpreadqrcode.data.repository


import android.util.ArrayMap
import android.util.Log
import com.gicproject.sohpreadqrcode.data.remote.MyApi
import com.gicproject.sohpreadqrcode.data.remote.dto.*
import com.gicproject.sohpreadqrcode.domain.model.*
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject


private const val TAG = "MyRepositoryImpl"
class MyRepositoryImpl @Inject constructor(
    private val api: MyApi
): MyRepository {



    override suspend fun checkQrCode(appId: String): List<ResultClass>? {
        return api.checkQrCode(appId)
    }


}