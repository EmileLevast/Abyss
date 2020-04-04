package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Player
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

    private fun <T:PassivePermanentPower> getPower(eventPowerTriggered:T):List<T>
    {
        return (mapCurrentActivePower[eventPowerTriggered.getKeyForMap()] as? List<T>) ?: listOf(eventPowerTriggered)
    }

    fun <T:PassivePermanentPower> applyToCorrespondingEvent(eventPowerTriggered:T,playerAttacked: Player,action: (t:T)->Unit)
    {
        getPower(eventPowerTriggered).forEach {

            //si c'est pas un pouvoir militaire
            if(it !is PassivePowerInfluenceOthers)
            {
                //alors on fait le pouvoir comme normal
                it.let(action)
            }
            else
            {
                //sinon on envoie le pouvoir a executer a cette fonction
                applyPowerReactingToMilitaryLord(it,playerAttacked)
            }
        }
    }

    /**method to check if the player has somehting to do with
     * one of his other power before resolve the effects of the attack [powerAttack]**/
    fun applyPowerReactingToMilitaryLord(powerAttack: InstantEffectPower,playerAttacked: Player)
    {
        //on recupere tous les pouvoirs qui se declenche lors de l'attaque d'un seigneur
        //on regarde si y en a pas un qui empeche le pouvoirde s'executer, sinon on l'execute
        getPower(object : MilitaryLordAttack{}).find {power -> !power.isAttackAvailable() } ?: powerAttack.activate(playerAttacked,
            controller!!.game)
    }
}


