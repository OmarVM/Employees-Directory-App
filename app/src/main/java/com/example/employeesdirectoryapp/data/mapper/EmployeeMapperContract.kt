package com.example.employeesdirectoryapp.data.mapper

import com.example.employeesdirectoryapp.data.entity.server.EmployeeRemote

interface EmployeeMapperContract<Employee, EmployeeRemote> {
    fun mapToEmployee(employeeRemote: EmployeeRemote): Employee
}