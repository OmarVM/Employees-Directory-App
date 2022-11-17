package com.example.employeesdirectoryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.employeesdirectoryapp.R
import com.example.employeesdirectoryapp.data.mapper.ResponseHandler
import com.example.employeesdirectoryapp.databinding.ActivityMainBinding
import com.example.employeesdirectoryapp.viewmodel.MainViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listEmployees.observe(this) { _info ->
            when(_info){
                is ResponseHandler.LOADING -> {
                    _info.progress?.let { _value ->
                        Log.d("LOGGER", "Loading... $_value")
                        binding.employeesRv.visibility = View.INVISIBLE
                        binding.mainProgressBar.visibility = View.VISIBLE
                        binding.mainProgressBar.progress = _value
                        binding.llWelcomeStatus.visibility = View.GONE
                    }
                }
                is ResponseHandler.SUCCESS -> {
                    Log.d("LOGGER", "Success -> Size: ${_info.data?.size}")
                    binding.mainProgressBar.visibility = View.GONE
                    val mAdapter = _info.data?.let { AdapterEmployees(it) }
                    binding.employeesRv.adapter = mAdapter
                    binding.employeesRv.visibility = View.VISIBLE
                }
                is ResponseHandler.ERROR -> {
                    binding.llWelcomeStatus.visibility = View.GONE
                    binding.mainProgressBar.visibility = View.GONE
                    binding.llErrorStatus.visibility = View.VISIBLE
                    Log.d("LOGGER", "Error: ${_info.message}")
                }
            }
        }

        mainViewModel.notifyRequestReady.observe(this) { _msg ->
            Toast.makeText(this, _msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.load_data -> {
                mainViewModel.getListEmployees()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}