package com.gicproject.salamattendanceapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gicproject.salamattendanceapp.data.remote.MyApi
import com.gicproject.salamattendanceapp.common.Constants
import com.gicproject.salamattendanceapp.data.data_source.MyDatabase
import com.gicproject.salamattendanceapp.data.repository.DataStoreRepositoryImpl
import com.gicproject.salamattendanceapp.data.repository.MyRepositoryImpl
import com.gicproject.salamattendanceapp.domain.repository.DataStoreRepository
import com.gicproject.salamattendanceapp.domain.repository.MyRepository
import com.gicproject.salamattendanceapp.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMyDatabase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            MyDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): DataStoreRepository = DataStoreRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideMyApi(): MyApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRepository(api: MyApi, db: MyDatabase): MyRepository {
        return MyRepositoryImpl(api = api, dao = db.myDao)
    }

    @Provides
    @Singleton
    fun provideMyUseCases(
        repository: MyRepository,
        dataStoreRepository: DataStoreRepository
    ): MyUseCases {
        return MyUseCases(
            getCustomerInputs = GetCustomerInputs(repository),
            deleteCustomerInput = DeleteCustomerInput(repository),
            addCustomerInput = AddCustomerInput(repository),
            getCustomerInput = GetCustomerInput(repository),
            getAttendance = GetAttendance(repository = repository),
            getCheckQrCode = GetCheckQrCode(
                repository = repository,
                dataStoreRepository = dataStoreRepository
            ),
            submitAnswer = SubmitAnswer(repository = repository),
            getLocations = GetLocations(repository = repository),
            getClinics = GetClinics(repository = repository)
        )
    }
}