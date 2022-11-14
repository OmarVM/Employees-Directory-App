package com.example.employeesdirectoryapp.usecase

import com.example.employeesdirectoryapp.data.entity.Employee
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import com.example.employeesdirectoryapp.data.repository.NetworkEmployeesRepository
import javax.inject.Inject

class GetEmployeesListUseCase @Inject constructor(
    private val networkEmployeesRepository: NetworkEmployeesRepository
) {
    suspend fun getEmployeesList(): ResponseHandler<List<Employee>> {
        return networkEmployeesRepository.getListEmployeesList()
    }
}