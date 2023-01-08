package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.salamattendanceapp.common.Constants
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.data.remote.dto.EmployeeDto
import com.gicproject.salamattendanceapp.domain.model.CheckOtpSend
import com.gicproject.salamattendanceapp.domain.model.CheckQrCodeSend
import com.gicproject.salamattendanceapp.domain.model.ResultClass
import com.gicproject.salamattendanceapp.domain.repository.DataStoreRepository
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
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