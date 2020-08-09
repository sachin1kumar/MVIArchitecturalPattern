package com.manager.retrofitesting.model.api

import com.manager.mviarchitecturalpattern.model.Data
import com.manager.mviarchitecturalpattern.model.Employee
import retrofit2.Call

interface ApiHelper {

    fun getEmployeeDetails(id: Int): Call<Employee>
}