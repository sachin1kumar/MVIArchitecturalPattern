package com.manager.mviarchitecturalpattern.mvicomponents

/**
 * Actor responsible for passing user actions to a consuming function.
 *
 * @param emit A [Unit] that emits an [MviAction] to a receiver
 *
 * Note: We pass a unit rather than the ViewModel, this helps to better separate logic. The XML doesn't need the
 * ViewModel, only something to handle state changes
 */
class MviActor(private val emit: (MviAction) -> Unit) {

    /**
     * The user clicked the add button intending to get data from backend/model to the current value in the state
     */
    fun addClicked(id: Int) = emit(MviAction.GetData(id))
}