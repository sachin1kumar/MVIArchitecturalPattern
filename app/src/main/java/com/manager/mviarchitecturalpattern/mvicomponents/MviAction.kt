package com.manager.mviarchitecturalpattern.mvicomponents

/**
 * Represent and Action taken by the user from MainActivity.
 */
sealed class MviAction {
    /**
     * Action type indicating the user desired to add the given [value] to the current state
     */
    class GetData(val id: Int) : MviAction()
}