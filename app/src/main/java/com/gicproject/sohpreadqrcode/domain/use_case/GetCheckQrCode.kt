package com.gicproject.sohpreadqrcode.domain.use_case


import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.common.Resource
import com.gicproject.sohpreadqrcode.domain.model.ResultClass
import com.gicproject.sohpreadqrcode.domain.repository.DataStoreRepository
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetCheckQrCode @Inject constructor(
    private val repository: MyRepository,
) {
    operator fun invoke(appId: String): Flow<Resource<ResultClass>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.checkQrCode(appId = appId)
            if(!result.isNullOrEmpty()){


                when (result[0].Message) {
                        "Updated" -> {
                            emit(Resource.Success(result[0]))
                        }
                        "No Data" -> {
                            emit(Resource.Error("${result[0].Message}: Appointment Not Found"))
                        }
                        "Error" -> {
                            emit(Resource.Error("${result[0].Message}"))
                        }
                        else -> {
                            emit(Resource.Error("${result[0].Message}"))
                        }
                    }




            }else{
                emit(Resource.Error("Empty CheckQrCode List."))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}