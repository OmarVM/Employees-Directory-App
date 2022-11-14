package com.example.employeesdirectoryapp.data.api

import com.example.employeesdirectoryapp.constants.EndPoints
import com.example.employeesdirectoryapp.data.entity.server.ListEmployeesRemote
import retrofit2.Response
import retrofit2.http.GET

interface EmployeesAPI {

    @GET(EndPoints.EMPLOYEES)
    suspend fun getEmployeesList(): Response<ListEmployeesRemote>
}