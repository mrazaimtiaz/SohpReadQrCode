package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.emojisurveyapp.domain.model.Answer
import com.gicproject.emojisurveyapp.domain.model.ResultId
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAttendance @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(isCheckIn: Boolean): Flow<Resource<ResultId>> = flow {
        try {
            emit(Resource.Loading())

            var answers = repository.getAnswers("1")
            if (!answers.isNullOrEmpty()) {
                answers = answers.sortedBy { it.Value }
                emit(Resource.Success(ResultId(1)))

            } else {
                emit(Resource.Error("Empty Question List."))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}