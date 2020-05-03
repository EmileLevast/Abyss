package fr.emile.abyss.modelClass

import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.BuyLordColorAllie
import fr.emile.abyss.modelClass.gameItems.BuyLordPrice
import fr.emile.abyss.modelClass.gameItems.Lord

//nombre des seigneurs disponible a la court
const val NUMBER_VISIBLE_LORD=33
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

        deckLord.addAll(Lord.listLord.toList())
        deckLord.shuffle()

        for(i in 1..NUMBER_VISIBLE_LORD)listProposedLord.add(deckLord.removeAt(0))
    }

    fun playerWantToBuy(player:Player,lordToBuy: Lord)
    {

        //we take only the allie that the player selected
        val listCardToBuy=player.listAlly.filter{ allie->allie.selectedToBuyLord}

        //We retrieve all the different type that are used
        val listDifferentType=listCardToBuy.map{allie -> allie.type }.distinct()

        //we calculate the cost of the lord (depending on power or not
        var purchasePrice=lordToBuy.price
        player.listRulesPower.applyToCorrespondingEvent<BuyLordPrice>(object : BuyLordPrice {},player)
        { purchasePrice=it.computePrice(purchasePrice) }

        //This parameter is used to determine whether the player has the obliged type to buy the lord
        //or of he has the power to change thi rule
        //i.e. Le Diplomate's function always return true
        var isTypeAllieValidForBuying=false
        player.listRulesPower.applyToCorrespondingEvent<BuyLordColorAllie>(object : BuyLordColorAllie{},player)
        {isTypeAllieValidForBuying=isTypeAllieValidForBuying||(it.isAuthorizedToBuy(listDifferentType,lordToBuy))}

        //si il ya le numbre de type d'allie et si il y a un allie obligatoire demande est-il la, alors on peut acheter
        if(listDifferentType.size>=lordToBuy.numberAllieType
            && lordToBuy.obligedType!= null && isTypeAllieValidForBuying)
        {

            //we calculate the sum that the chosen allies give
            val sumValueAllie:Int=listCardToBuy.fold(0) { sum, allie->sum+allie.number}
            //s'il y a le prix
            if(sumValueAllie>=purchasePrice)
            {
                //on enleve le seigneur et on l'ajoute a la liste des seigneurs achetés du joueur
                player.buyLord(getLordAndRemoveItFromProposed(lordToBuy),listCardToBuy)
                lordIsActuallyBought(player)
                //si le joueur a achete quelque chose il finit son tour
                controller!!.courtFinish(player,lordToBuy)
            }

        }
    }

    /**Implement here what happens to the court when a lord is purchased**/
    fun lordIsActuallyBought(playerBuying:Player)
    {

        //si le joueur tire l'entepenultieme seigneur il gagne 2 perles
        if(drawNewLords())
        {
            playerBuying.perl+=2
        }
    }

    fun drawNewLords() :Boolean
    {
        if(listProposedLord.size<=2)
        {
            //on remplit avec des nouveaux seigneurs
            while(listProposedLord.size< NUMBER_VISIBLE_LORD)
            {
                drawAndAddLordToCourt()
            }
            return true
        }

        return false
    }

    fun getLordAndRemoveItFromProposed(lordToRetrieve:Lord):Lord
    {
        return listProposedLord.removeAt(listProposedLord.indexOf(lordToRetrieve))
    }

    /**Draw Lord, and return it. It is not added to the proposed lords list**/
    fun drawOneLord():Lord
    {
        return deckLord.removeAt(0)
    }

    fun drawAndAddLordToCourt()
    {
        listProposedLord.add(drawOneLord())
    }

    override fun toString(): String {
        return "=====Court====="+listProposedLord.joinToString(separator = "\n"){ it.toString()}
    }
}