package com.gicproject.sohpreadqrcode.domain.use_case


import com.gicproject.sohpreadqrcode.common.Resource
import com.gicproject.sohpreadqrcode.data.remote.dto.EmployeeDto
import com.gicproject.sohpreadqrcode.domain.model.CheckPersonalInfoSend
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetEmployeeInfoCode @Inject constructor(
    private val repository: MyRepository,
) {
    operator fun invoke(checkPersonalInfoSend: CheckPersonalInfoSend): Flow<Resource<EmployeeDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.personalInfo(checkPersonalInfoSend)
            if(!result.isNullOrEmpty()){
                if(result[0].status == 1){
                    emit(Resource.Success(result[0]))
                }else{
                    emit(Resource.Error("${result[0].message} \n ${result[0].message}"))
                }

            }else{
                emit(Resource.Error("Empty EmployeeInfo List."))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}