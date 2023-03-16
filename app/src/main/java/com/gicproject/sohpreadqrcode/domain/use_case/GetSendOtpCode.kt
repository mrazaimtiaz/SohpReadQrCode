package com.gicproject.sohpreadqrcode.domain.use_case


import com.gicproject.sohpreadqrcode.common.Resource
import com.gicproject.sohpreadqrcode.domain.model.CheckOtpSend
import com.gicproject.sohpreadqrcode.domain.model.ResultClass
import com.gicproject.sohpreadqrcode.domain.repository.DataStoreRepository
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetSendOtpCode @Inject constructor(
    private val repository: MyRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(checkOtpSend: CheckOtpSend): Flow<Resource<ResultClass>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.sendOtp(checkOtpSend)
            if(!result.isNullOrEmpty()){
                if(result[0].status == 1){
                    emit(Resource.Success(result[0]))
                }else{
                    emit(Resource.Error("${result[0].Message} \n ${result[0].MessageAr}"))
                }

            }else{
                emit(Resource.Error("Empty SendOtp List."))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}