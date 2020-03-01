package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Player

//nombre des seigneurs disponible a la court
const val NUMBER_VISIBLE_LORD=7
//court des seigneurs avec tous les seigneurs dispos
class Court {

    //contient tous les seigneurs restants de la pioche
    var deckLord= mutableListOf<Lord>()

    //contient les seigneurs de la court
    var listProposedLord= mutableListOf<Lord>()
    init {
        initalize()
    }

    fun initalize()
    {
        deckLord.clear()
        listProposedLord.clear()

        deckLord.addAll(Deck.listLord)
        deckLord.shuffle()

        for(i in 1..NUMBER_VISIBLE_LORD)listProposedLord.add(deckLord.removeAt(0))
    }

    fun playerWantToBuy(player:Player,lordToBuy:Lord)
    {

        //we take only the allie that the player selected
        val listCardToBuy=player.listAllie.filter{allie->allie.selectedToBuyLord}
        //we calculate the sum that the chosen allies give
        val sumValueAllie:Int=listCardToBuy.fold(0) { sum, allie->sum+allie.number}
        //We retrieve all the different type that are used
        val listDifferentType=listCardToBuy.map{allie -> allie.type }.distinct()

        //we calculate the cost of the lord (depending on power or not
        var purchasePrice=lordToBuy.price
        player.listRulesPower.getPower(object : BuyLordPrice{}).forEach { purchasePrice=it.computePrice(purchasePrice) }


        //si il ya le prix, le numbre de type d'allie et l'allie obligatoire, alors on peut acheter
        if(sumValueAllie>=purchasePrice && listDifferentType.size>=lordToBuy.numberAllieType &&
            listDifferentType.contains(lordToBuy.obligedType))
        {
            //on enleve le seigneur et on l'ajoute a la liste des seigneurs achetés du joueur
            player.buyLord(listProposedLord.removeAt(listProposedLord.indexOf(lordToBuy)),listCardToBuy)
            //si le joueur tire l'entepenultieme seigneur il gagne 2 perles
            if(drawNewLords())player.perl+=2

            //the player actually buy something

            //si le joueur a achete quelque chose il finit son tour
            controller!!.courtFinish(player)
        }


    }

    fun drawNewLords() :Boolean
    {
        if(listProposedLord.size<=2)
        {
            //on remplit avec des nouveaux seigneurs
            while(listProposedLord.size< NUMBER_VISIBLE_LORD)
            {
                listProposedLord.add(deckLord.removeAt(0))
            }
            return true
        }

        return false
    }

    override fun toString(): String {
        return "=====Court====="+listProposedLord.joinToString(separator = "\n"){ it.toString()}
    }
}