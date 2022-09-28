package com.gicproject.salamattendanceapp.domain.use_case

import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.emojisurveyapp.domain.model.InvalidCustomerInputException
import com.gicproject.emojisurveyapp.domain.model.Question
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AddCustomerInput(
    private val repository: MyRepository
) {
    operator fun invoke(customerSupport: CustomerInput): Flow<Resource<Long?>> = flow {
        try {
          val id =  repository.insertCustomerSupport(customerSupport)
            emit(Resource.Success(id))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }


}