package com.example.employeesdirectoryapp.di

import com.example.employeesdirectoryapp.data.repository.NetworkEmployeesRepository
import com.example.employeesdirectoryapp.usecase.GetEmployeesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideEmployeeListUseCase(
        repository: NetworkEmployeesRepository
    ): GetEmployeesListUseCase {
        return GetEmployeesListUseCase(repository)
    }
}