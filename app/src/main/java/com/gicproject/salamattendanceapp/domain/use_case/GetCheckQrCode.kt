package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.salamattendanceapp.common.Constants
import com.gicproject.salamattendanceapp.common.Constants.Companion.KEY_IS_SHOW_SUBMIT
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.data.remote.dto.EmployeeDto
import com.gicproject.salamattendanceapp.domain.repository.DataStoreRepository
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


var globalId = 0
class GetCheckQrCode @Inject constructor(
    private val repository: MyRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Resource<EmployeeDto>> = flow {
        try {
            emit(Resource.Loading())
            val questions = repository.getQuestions()
            if(!questions.isNullOrEmpty()){
                questions[0].showInfoPage?.let {
                    dataStoreRepository.putBoolean(KEY_IS_SHOW_SUBMIT,
                        it
                    )
                }
                globalId += 1
                var emp = EmployeeDto(globalId,"Mohammad", designation = "Programmer");
                if(emp.image.isNullOrEmpty()){
                   emp.image = Constants.baseImage
                }
                emit(Resource.Success(emp))
            }else{
                emit(Resource.Error("Empty Question List."))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}