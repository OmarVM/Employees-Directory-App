package com.example.employeesdirectoryapp.data.repository

import com.example.employeesdirectoryapp.data.api.EmployeesAPI
import com.example.employeesdirectoryapp.data.entity.Employee
import com.example.employeesdirectoryapp.data.mapper.EmployeeMapper
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import javax.inject.Inject

class NetworkEmployeesRepository @Inject constructor(
    private val employeesAPI: EmployeesAPI,
    private val mapper: EmployeeMapper
) {
     suspend fun getListEmployeesList(): ResponseHandler<List<Employee>> {
        val response = employeesAPI.getEmployeesList()
         if (response.isSuccessful) {
             response.body()?.let { _list ->
                 val mList = mapper.toEmployeeList(_list.employees)
                 return ResponseHandler.SUCCESS<List<Employee>>(mList)
             }
         }
         return ResponseHandler.ERROR(message = response.errorBody().toString())
     }
}