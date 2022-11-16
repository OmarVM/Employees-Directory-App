package com.example.employeesdirectoryapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.employeesdirectoryapp.R
import com.example.employeesdirectoryapp.data.entity.Employee
import com.example.employeesdirectoryapp.databinding.RowEmployeeBinding
import com.google.android.material.circularreveal.CircularRevealHelper.Strategy

class AdapterEmployees (private val listItems: List<Employee>) : RecyclerView.Adapter<AdapterEmployees.EmployeeViewHolder>() {

    private lateinit var binding: RowEmployeeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowEmployeeBinding.inflate(inflater, parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

    inner class EmployeeViewHolder(private val view: RowEmployeeBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(item: Employee) {
            binding.apply {
                rowEmployeeName.text = item.fullName
                rowEmployeeTeam.text = item.team
                Glide
                    .with(view.root)
                    .load(item.photoUrlSmall)
                    .centerCrop()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_img_default)
                    .into(rowEmployeePhoto)
            }
        }
    }
}