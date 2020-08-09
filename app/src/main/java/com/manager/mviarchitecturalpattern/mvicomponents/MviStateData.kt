package com.manager.mviarchitecturalpattern.mvicomponents

/**
 * Data class representing the state of the view. Any persistent state should be annotated here
 *
 * @param employeeName The current String value
 * @param toPassEmployeeId Default id to be passed to model to get the data from the backend.
 */
data class MviStateData(
    val employeeName: String = "",
    val toPassEmployeeId: Int = 1
)