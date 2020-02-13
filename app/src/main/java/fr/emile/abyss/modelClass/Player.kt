package fr.emile.abyss.modelClass

import fr.emile.abyss.modelClass.gameItems.Allie
import fr.emile.abyss.modelClass.gameItems.Lord

class Player (var nom:String){

    //true quand le joueur a deja achete un allie dans cette explo
    var dejaAcheteExplo=false

    var listAllieFedere= mutableListOf<Allie>()
    var listLord= mutableListOf<Lord>()

    var perl=1
    var listAllie=mutableListOf<Allie>()

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
}