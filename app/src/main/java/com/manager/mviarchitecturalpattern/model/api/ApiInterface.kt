package com.manager.retrofitesting.model.api

import com.manager.mviarchitecturalpattern.model.Data
import com.manager.mviarchitecturalpattern.model.Employee
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api/v1/employee/{id}")
    fun getEmployeeDetails(@Path("id") id: Int): Call<Employee>
}