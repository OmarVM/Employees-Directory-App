package com.example.employeesdirectoryapp.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeesdirectoryapp.data.entity.Employee
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import com.example.employeesdirectoryapp.usecase.GetEmployeesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEmployeesListUseCase: GetEmployeesListUseCase
): ViewModel() {

    private val _listEmployees = MutableLiveData<ResponseHandler<List<Employee>>>()
    val listEmployees: LiveData<ResponseHandler<List<Employee>>> = _listEmployees
    private val _notifyRequestReady = MutableLiveData<String>()
    val notifyRequestReady:LiveData<String> = _notifyRequestReady
    private var networkRequestEnabled: Boolean = true
    private var leftTime:Long = 0

    private val countDown: CountDownTimer = object : CountDownTimer(15000, 1000) {
        override fun onTick(p0: Long) {
            leftTime = p0 /1000
            Log.d("Logger", "${leftTime}s to unlock.")
        }

        override fun onFinish() {
            networkRequestEnabled = true
            Log.d("Logger", "Time OnFinish()")
            _notifyRequestReady.postValue("Ready to Update info.")
        }

    }

    fun getListEmployees() {
        if (networkRequestEnabled) {
            networkRequestEnabled = false
            countDown.start()
            viewModelScope.launch {
                startProgress()
                val response = getEmployeesListUseCase.getEmployeesList()
                _listEmployees.postValue(response)
            }
        } else {
            _notifyRequestReady.postValue("${leftTime.toString()}s left")
        }
    }

    private suspend fun startProgress() {
        for (i in 20..100 step 20) {
            delay(500)
            _listEmployees.postValue(ResponseHandler.LOADING(i))
        }
    }
}