package fr.emile.abyss.modelClass

import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.modelClass.gameItems.*

private const val NBR_MAX_LORD=7

class Player (var nom:String, override var imgId:Int):IShowImage{

    //true quand le joueur a deja achete un allie dans cette explo
    var dejaAcheteExplo=false

    //contains all the monster tokens that the player won and didn't use yet
    var listMonsterToken= mutableListOf<MonsterToken>()

    var listAllieFedere= mutableListOf<Ally>()
    var listLord= mutableListOf<Lord>()

    var perl=1
    var listAlly=mutableListOf<Ally>()

    var listRulesPower:RulesPower= RulesPower()

    fun buyAllieCard(cardToAdd:Ally, cost:Int)
    {
        listAlly.add(cardToAdd)
        perl-=cost

        dejaAcheteExplo=true
    }

    /**Take the [lordToBuy] and add it to the purchased Lords [listLord] , and federate the allie**/
    fun buyLord(lordToBuy:Lord, listAllyUsedToBuy:List<Ally>)
    {
        addLord(lordToBuy)
        /**!!Surtout ne pas supprimer les allies qui ont servi a l'achat car il faut encore les envoyer à la defausse!!**/

        //federate allies
        val federatedAllie=listAllyUsedToBuy.minBy { allieBuying->allieBuying.number}

        //on ajoute l'allie a la liste d'allie federe
        listAllieFedere.add(federatedAllie!!)

    }

    /**Use this to add a lord the hand of player**/
    fun addLord(lordToBuy:Lord)
    {
        listLord.add(lordToBuy)
    }

    fun addAllie(cardToAdd:Ally)
    {
        listAlly.add(cardToAdd)
    }

    fun addAllie(listCardToAdd:MutableList<Ally>)
    {
        listAlly.addAll(listCardToAdd)
    }

    fun hasMaxNbrLord():Boolean
    {
        return listLord.size>= NBR_MAX_LORD
    }


    /**call this function to execute a non-passive miltary power**/
    fun playerUnderAttackMilitaryLord(powerEffect:(playerAttacking:Player,Game)->Unit,actionOnUnvalidAttack:()->Unit={})
    {
        listRulesPower.applyPowerReactingToMilitaryLord(powerEffect,this)
    }
}