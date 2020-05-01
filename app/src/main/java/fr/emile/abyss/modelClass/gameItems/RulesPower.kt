package fr.emile.abyss.modelClass.gameItems

import android.util.Log
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player
import java.util.*


/**Contain all the rules that a player has , how does he federate, how does he count the price of a lord...
 * The rules can be changed by a power**/
class RulesPower {

    private val mapCurrentActivePower:HashMap<String,LinkedList<PassivePermanentPower>> = hashMapOf()


    fun addPower(power:PassivePermanentPower)
    {
        val key=power.getKeyForMap()
        Log.w("msg","interface name:$key")
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

    /**Use this method to trigger the [PassivePermanentPower]
     * Always define the generic type do not infer it with the parameter
     * Otherwise you will have cast issues**/
    fun <T:PassivePermanentPower> applyToCorrespondingEvent(eventPowerTriggered:T, player: Player, action: (t:T)->Unit)
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
                //ici s'il y a une attaque c'est forcément le joueur lui même qui se fait attaquer donc c'est logique
                //d'envoyer la variable player pour le paramètre playerAttacked
                applyPowerReactingToMilitaryLord({_,_->it.let(action)},player)
            }
        }
    }

    /**method to check if the player has somehting to do with
     * one of his other power before resolve the effects of the attack [powerAttack]
     * And if the military power is Authorized (i.e. Chamanesse)
     * I use a lambda and not an InstantEffectPower because I didn't find the way to deal deal otherwise
     * with the assassin power**/
    fun applyPowerReactingToMilitaryLord(powerEffect:(playerAttacking:Player, Game)->Unit,playerAttacked: Player)
    {
        //on recupere tous les pouvoirs qui se declenche lors de l'attaque d'un seigneur
        //on regarde si y en a pas un qui empeche le pouvoirde s'executer, sinon on l'execute
        getPower<MilitaryLordAttack>(object : MilitaryLordAttack{}).find {power -> !power.isAttackAvailable() } ?:
        powerEffect(playerAttacked, controller!!.game)
    }
}


