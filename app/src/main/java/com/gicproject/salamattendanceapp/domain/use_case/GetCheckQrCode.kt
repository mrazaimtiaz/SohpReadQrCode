package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.salamattendanceapp.common.Constants
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.data.remote.dto.EmployeeDto
import com.gicproject.salamattendanceapp.domain.model.CheckQrCodeSend
import com.gicproject.salamattendanceapp.domain.repository.DataStoreRepository
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetCheckQrCode @Inject constructor(
    private val repository: MyRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(checkQrCodeSend: CheckQrCodeSend): Flow<Resource<EmployeeDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.checkQrCode(checkQrCodeSend)
            if(!result.isNullOrEmpty()){
                if(result[0].status == 1){
                    var emp = EmployeeDto(result[0].EmployeeCode,result[0].EmployeeName, departmentEn = "",iD = result[0].ID);
                    if(emp.image.isNullOrEmpty()){
                        emp.image = Constants.baseImage
                    }
                    emit(Resource.Success(emp))
                }else{
                    emit(Resource.Error("${result[0].Message} \n ${result[0].MessageAr}"))
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