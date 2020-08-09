package com.manager.mviarchitecturalpattern.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manager.mviarchitecturalpattern.model.Employee
import com.manager.mviarchitecturalpattern.mvicomponents.MviAction
import com.manager.mviarchitecturalpattern.mvicomponents.MviState
import com.manager.mviarchitecturalpattern.mvicomponents.MviStateData
import com.manager.retrofitesting.model.api.ApiHelperImpl
import com.manager.retrofitesting.model.api.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * [ViewModel] for the [MviActivity].
 */
class MviViewModel : ViewModel() {
    // keep this private so that only the ViewModel can modify the state
    private val state = MutableLiveData<MviState>()
        .apply { value = MviState.Content(MviStateData()) }

    // Create a publicly accessible LiveData object that can be observed
    val _state: LiveData<MviState> = state

    /**
     * Take an [MviAction] and process it
     */
    fun takeAction(action: MviAction) {
        when (action) {
            is MviAction.GetData -> handleAction(action.id)
        }
    }

    /**
     * Handle the action.
     *
     * Note: This should cover all outcomes, error or not
     */
    private fun handleAction(id: Int) {
        //to show loading.
        update(MviState.Loading)

        val apiHelper = ApiHelperImpl(RetrofitBuilder.apiInterface)
        apiHelper.getEmployeeDetails(id)
            .enqueue(object : Callback<Employee> {
                override fun onFailure(call: Call<Employee>, t: Throwable) {
                    update(MviState.InvalidNumberError)
                }

                override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                    val  newStateData = currentStateData convertResponse response.body()?.data?.employee_name
                    update(MviState.Content(newStateData))
                }
            })

    }

    private fun update(newState: MviState) {
        when (newState) {
            is MviState.Effect,
            is MviState.Loading,
            is MviState.Content -> state.postValue(newState)
        }
    }

    /**
     * Increment the current state's numeric value by [num]. This simplifies the operation of adding a [String]
     * representation of an [Int] to an [Int] and turning the result back to a [String].
     *
     * Note: This utilizes the copy function, which creates a new immutable data class from the old one. It helps
     * significantly when the state data grows complex
     */
    private infix fun MviStateData.convertResponse(employeeName: String?): MviStateData {
        return copy(employeeName = (employeeName!!))
    }

    /**
     * A helper to get the current state data object if it exists, otherwise returning a default value
     */
    private val currentStateData: MviStateData
        get() {
            val data = state.value?.let {
                return if (it is MviState.Content) {
                    it.stateData
                } else {
                    MviStateData()
                }
            }

            return data ?: MviStateData()
        }
}