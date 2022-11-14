package com.example.employeesdirectoryapp.data.entity.server

import com.squareup.moshi.Json

data class ListEmployeesRemote(
    @field:Json(name = "employees")
    val employees:List<EmployeeRemote>
)
