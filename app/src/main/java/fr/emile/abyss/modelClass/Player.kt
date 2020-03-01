package fr.emile.abyss.modelClass

import fr.emile.abyss.modelClass.gameItems.*

private const val NBR_MAX_LORD=7

class Player (var nom:String){

    //true quand le joueur a deja achete un allie dans cette explo
    var dejaAcheteExplo=false

    var listAllieFedere= mutableListOf<Allie>()
    var listLord= mutableListOf<Lord>()

    var perl=1
    var listAllie=mutableListOf<Allie>()

    var listRulesPower:RulesPower= RulesPower()

    fun buyAllieCard(cardToAdd:Allie,cost:Int)
    {
        listAllie.add(cardToAdd)
        perl-=cost

        dejaAcheteExplo=true
    }

    /**Take the [lordToBuy] and add it to the purchased Lords [listLord] , and federate the allie**/
    fun buyLord(lordToBuy:Lord,listAllieUsedToBuy:List<Allie>)
    {
        listLord.add(lordToBuy)

        /**!!Surtout ne pas supprimer les allies qui ont servi a l'achat car il faut encore les envoyer à la defausse!!**/

        //federate allies
        val federatedAllie=listAllieUsedToBuy.minBy {allieBuying->allieBuying.number}

        //on ajoute l'allie a la liste d'allie federe
        listAllieFedere.add(federatedAllie!!)

    }

    fun addAllie(cardToAdd:Allie)
    {
        listAllie.add(cardToAdd)
    }

    fun addAllie(listCardToAdd:MutableList<Allie>)
    {
        listAllie.addAll(listCardToAdd)
    }

    fun hasMaxNbrLord():Boolean
    {
        return listLord.size>= NBR_MAX_LORD
    }

    fun playerUnderAttackMilitaryLord(powerAttack: InstantEffectPower,game: Game)
    {
        //on itere a travers tous les pouvoirs concernant les attaques de seigneurs militaires
        //on demande de chercher le premier pouvoir qui interdit l'attaque du seigneur militaire.
        //s'il y en a pas, null est retourné ,ainsi avec l'operateur elvis on active le pouvoir
        listRulesPower.getPower(object : MilitaryLordAttack{}).
            find {power -> !power.isAttackAvailable() } ?: powerAttack.activate(this,game)
    }
}