package com.example.employeesdirectoryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeesdirectoryapp.data.entity.Employee
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import com.example.employeesdirectoryapp.usecase.GetEmployeesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEmployeesListUseCase: GetEmployeesListUseCase
): ViewModel() {

    private val _listEmployees = MutableLiveData<ResponseHandler<List<Employee>>>()
    val listEmployees: LiveData<ResponseHandler<List<Employee>>> = _listEmployees

    fun getListEmployees() {
        _listEmployees.postValue(ResponseHandler.LOADING())
        viewModelScope.launch {
            val response = getEmployeesListUseCase.getEmployeesList()
            _listEmployees.postValue(response)
        }
    }
}