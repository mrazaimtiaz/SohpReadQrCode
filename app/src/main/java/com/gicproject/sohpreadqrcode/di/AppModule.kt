package com.gicproject.sohpreadqrcode.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gicproject.sohpreadqrcode.data.remote.MyApi
import com.gicproject.sohpreadqrcode.common.Constants
import com.gicproject.sohpreadqrcode.data.repository.DataStoreRepositoryImpl
import com.gicproject.sohpreadqrcode.data.repository.MyRepositoryImpl
import com.gicproject.sohpreadqrcode.domain.repository.DataStoreRepository
import com.gicproject.sohpreadqrcode.domain.repository.MyRepository
import com.gicproject.sohpreadqrcode.domain.use_case.*
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
    fun provideMyRepository(api: MyApi): MyRepository {
        return MyRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideMyUseCases(
        repository: MyRepository,
    ): MyUseCases {
        return MyUseCases(
            getCheckQrCode = GetCheckQrCode(
                repository = repository,
            ),
        )
    }
}