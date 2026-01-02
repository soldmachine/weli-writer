package com.szoldapps.weli.writer.presentation.common.click_action

/**
 * A stand-in for an action that would normally be implemented as a lambda.
 *
 * Lets us make equality comparisons between entities that encode actions (e.g., presentation layer models with click
 * handlers), where the use of a lambda would cause otherwise identical entities to be deemed unequal or incomparable.
 *
 * In the click listener case it is unlikely that the listener needs to contribute to view entity state;
 * When a view entity is rebuilt, a new anonymous class is usually created for the listener but it would normally have
 * the same implementation.
 *
 * This is a very common pattern, and generally we only need to update the view if the listener goes from not being set
 * to being set (and vice versa).
 *
 * To accomplish this you can use the [ActionWrapper] class to model actions.
 */
class ActionWrapper<I, O>(
    private val comparingPayload: Any? = null,
    private val action: (I) -> O
) : (I) -> O {

    override fun invoke(input: I): O = execute(input)

    fun execute(input: I) = action(input)

    override fun equals(other: Any?): Boolean =
        other is ActionWrapper<*, *> && other.comparingPayload == comparingPayload

    override fun hashCode(): Int = super.hashCode()
}
