package com.manager.retrofitesting.model.api

import com.manager.mviarchitecturalpattern.model.Data
import com.manager.mviarchitecturalpattern.model.Employee
import retrofit2.Call

class ApiHelperImpl(private val apiInterface: ApiInterface) : ApiHelper {

    override fun getEmployeeDetails(id: Int): Call<Employee> = apiInterface.getEmployeeDetails(id)

}