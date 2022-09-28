package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.emojisurveyapp.domain.model.CustomerInput
import com.gicproject.emojisurveyapp.domain.model.Question
import com.gicproject.emojisurveyapp.domain.model.ResultId
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SubmitAnswer @Inject constructor(
    private val repository: MyRepository
) {
    operator fun invoke(
        customerInput: CustomerInput? = null,
        questionId: String,
        answerId: String,
        value: String,
        mobileNumber: String,
        deviceId: String,
        locationId: String,
        sendEmail: Boolean,
        clinicId: String,
        clinicName: String,
    ): Flow<Resource<ResultId>> = flow {
        try {
            emit(Resource.Loading())
            val resultIds = repository.submitAnswer(
               questionId =  questionId, answerId = answerId, value =value, mobileNumber = mobileNumber, deviceId =  deviceId, locationId =  locationId,sendEmail = sendEmail,clinicId = clinicId,clinicName = clinicName
            )
            if(!resultIds.isNullOrEmpty()){
                if(customerInput != null){
                    customerInput.isSaved = 1
                    repository.insertCustomerSupport(customerInput = customerInput)
                }
                emit(Resource.Success(resultIds[0].toResultId()))
            }else{
                emit(Resource.Error("Empty ResultId List."))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}