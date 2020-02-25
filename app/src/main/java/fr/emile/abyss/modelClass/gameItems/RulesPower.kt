package fr.emile.abyss.modelClass.gameItems

import java.util.*


/**Contain all the rules that a player has , how does he federate, how does he count the price of a lord...
 * The rules can be changed by a power**/
class RulesPower {

    private val mapCurrentActivePower:HashMap<String,LinkedList<PassivePermanentPower>> = hashMapOf()


    fun addPower(power:PassivePermanentPower)
    {
        val key=power.getKeyForMap()
        mapCurrentActivePower[key]?.add(power)
            ?: mapCurrentActivePower.put(key,LinkedList(listOf(power)))
    }

    fun deletePower(power:PassivePermanentPower)
    {
        val key=power.getKeyForMap()

        //we delete for referencial equality so keep same object in Lord and in Rules
        mapCurrentActivePower[key]?.removeIf { it===power }
    }

    fun <T:PassivePermanentPower> getPower(eventPowerTriggered:T):List<T>
    {
        return (mapCurrentActivePower[eventPowerTriggered.getKeyForMap()] as? List<T>) ?: listOf(eventPowerTriggered)
    }
}


