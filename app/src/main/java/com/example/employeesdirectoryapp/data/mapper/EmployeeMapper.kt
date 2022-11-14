package com.example.employeesdirectoryapp.data.mapper

import com.example.employeesdirectoryapp.data.entity.Employee
import com.example.employeesdirectoryapp.data.entity.server.EmployeeRemote

class EmployeeMapper() : EmployeeMapperContract<Employee, EmployeeRemote> {

    override fun mapToEmployee(employeeRemote: EmployeeRemote): Employee {
        return Employee(
            uuid = employeeRemote.uuid,
            fullName = employeeRemote.fullName,
            phoneNumber = employeeRemote.phoneNumber,
            emailAddress = employeeRemote.emailAddress,
            biography = employeeRemote.biography
        )
    }

    fun toEmployeeList(mList: List<EmployeeRemote>): List<Employee> {
        return mList.map { _item ->
            mapToEmployee(_item)
        }
    }
}
