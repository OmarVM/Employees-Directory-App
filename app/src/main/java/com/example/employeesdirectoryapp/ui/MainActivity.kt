package com.example.employeesdirectoryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.employeesdirectoryapp.R
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import com.example.employeesdirectoryapp.viewmodel.MainViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.listEmployees.observe(this) { _info ->
            when(_info){
                is ResponseHandler.LOADING -> Log.d("LOGGER", "Loading...")
                is ResponseHandler.SUCCESS -> Log.d("LOGGER", "Success -> Size: ${_info.data?.size}")
                is ResponseHandler.ERROR -> Log.d("LOGGER", "Error: ${_info.message}")
            }
        }

        mainViewModel.getListEmployees()
    }
}