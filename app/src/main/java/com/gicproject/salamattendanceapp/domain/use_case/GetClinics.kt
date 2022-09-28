package com.gicproject.salamattendanceapp.domain.use_case


import com.gicproject.emojisurveyapp.domain.model.Clinic
import com.gicproject.salamattendanceapp.common.Resource
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetClinics @Inject constructor(
    private val repository: MyRepository,
) {
    operator fun invoke(
        id: String): Flow<Resource<List<Clinic>>> = flow {
        try {
            emit(Resource.Loading())

            var clinics = repository.getClinics(id =id)
            if (!clinics.isNullOrEmpty()) {
                emit(Resource.Success(clinics.map {
                    it.toClinic()
                }))
                //emit(Resource.Success( listOf(clinics.get(0).toClinic()) ))
            } else {
                emit(Resource.Error("Empty Location List."))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}