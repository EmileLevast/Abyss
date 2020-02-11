package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Player

//nombre des seigneurs disponible a la cour
const val NUMBER_VISIBLE_LORD=7
//cour des seigneurs avec tous les seigneurs dispos
class Cour {

    //contient tous les seigneurs restants de la pioche
    var deckLord= mutableListOf<Lord>()

    //contient les seigneurs de la cour
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
        //does the player buy or something or not
        var playerBought=false

        val listCardToBuy=player.listAllie.filter{allie->allie.selectedToBuyLord}
        val sumValueAllie:Int=listCardToBuy.fold(0) { sum, allie->sum+allie.number}
        val listDifferentType=listCardToBuy.map{allie -> allie.type }.distinct()

        //si il ya le prix, le numbre de type d'allie et l'allie obligatoire, alors on peut acheter
        if(sumValueAllie>=lordToBuy.price && listDifferentType.size>=lordToBuy.numberAllieType &&
            listDifferentType.contains(lordToBuy.obligedType))
        {
            //on enleve le seigneur et on l'ajoute a la liste des seigneurs achetés du joueur
            player.buyLord(listProposedLord.removeAt(listProposedLord.indexOf(lordToBuy)),listCardToBuy)
            //si le joueur tire l'entepenultieme seigneur il gagne 2 perles
            if(drawNewLords())player.perl+=2

            //the player actually buy something
            playerBought=true
        }

        controller!!.courtFinish(playerBought,player)

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
        return "=====Cour====="+listProposedLord.joinToString(separator = "\n"){ it.toString()}
    }
}