package com.example.employeesdirectoryapp.di

import com.example.employeesdirectoryapp.constants.DaggerConst
import com.example.employeesdirectoryapp.constants.EndPoints
import com.example.employeesdirectoryapp.data.api.EmployeesAPI
import com.example.employeesdirectoryapp.data.mapper.EmployeeMapper
import com.example.employeesdirectoryapp.data.repository.NetworkEmployeesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {

    @Singleton
    @Provides
    @Named(DaggerConst.BASE_URL)
    fun provideBaseURL() = EndPoints.Base_URL_PROD

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named(DaggerConst.BASE_URL)
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeesAPI(
        retrofit: Retrofit
    ): EmployeesAPI {
        return retrofit.create(EmployeesAPI::class.java)
    }

    @Provides
    fun provideMapper() = EmployeeMapper()

    @Provides
    fun provideNetworkEmployeesRepository(
        employeesAPI: EmployeesAPI,
        employeeMapper: EmployeeMapper
    ): NetworkEmployeesRepository {
        return NetworkEmployeesRepository(employeesAPI, employeeMapper)
    }
}