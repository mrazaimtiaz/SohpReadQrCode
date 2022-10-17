package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.emojisurveyapp.domain.model.ResultId
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.domain.model.CheckSend
import com.gicproject.salamattendanceapp.domain.model.ResultClass
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAttendance @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(checkSend: CheckSend, isCheckIn: Boolean): Flow<Resource<ResultClass>> = flow {
        try {
            emit(Resource.Loading())

            var result = repository.checkSend(checkSend,isCheckIn)
            if (!result.isNullOrEmpty()) {
                if(result[0].status == 1){
                    emit(Resource.Success(result[0]))
                }else{
                    emit(Resource.Error((result[0].Message ?: "") + "\n" + (result[0].MessageAr ?: "")))
                }

            } else {
                emit(Resource.Error("Empty Result List."))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}