package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.ContainerPlayerExplo
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.Allie
import fr.emile.abyss.modelClass.gameItems.Deck

const val NUMBER_MAX_CARD_EXPLO_SHOW=8

class Exploration (listPlayer:Container<Player>){

    lateinit var listPlayer: ContainerPlayerExplo

    //created one time, same for all explorations because we keep the same object Exploration during all the game
    var deckAllie=Deck().stackAllie

    //register all the visible cards
    var listProposedCard=mutableListOf<Allie>()

    //refer to the player who is currently choosing between buy or not the card
    //lateinit var playerCurrentlyChoosing:ListIterator<Player>
    //var currentChoosingPlayer=0

    var currentCost=1

    init {
        initializeExplo(listPlayer)
    }

    //Always called when generate a new exploration
    fun initializeExplo(listPlayer:Container<Player>)
    {
        listProposedCard.clear()
        this.listPlayer= ContainerPlayerExplo(listPlayer.listElt,listPlayer.index)
        currentCost=1
        //this.deckAllie=deckAllie

        //aucun des jeourus n'a achete d'allie pour cette explo
        this.listPlayer.listElt.forEach { it.dejaAcheteExplo=false }
    }

    fun explore()
    {
        if(!deckAllie.isEmpty())//si il reste encore des cartes a tirer
        {
            //we take card from the deck and add it to the visible card
            listProposedCard.add(deckAllie.removeAt(0))
        }

        listPlayer.reset()
    }

    fun playerMakeChoice(choice:Choice)
    {

        //si le dernier joueur prend la carte ou qu'il est a la fin
        if(listPlayer.isMainPlayer())
        {
            if(choice==Choice.BUY||listProposedCard.size== NUMBER_MAX_CARD_EXPLO_SHOW)
            {
                playerWhoLaunchedExlpoTakeAllie(listProposedCard.removeAt(listProposedCard.size-1))
            }else
            {

                explore()
            }
        }else //si c'est un autre joueur
        {
            //si il veut acheter et qu'il a pas encore achete
            if(choice==Choice.BUY&&
                !listPlayer.getCurrent().dejaAcheteExplo&&listPlayer.getCurrent().perl>=currentCost)
            {
                listPlayer.getCurrent().buyAllieCard(listProposedCard.removeAt(listProposedCard.size-1),currentCost)

                //the player who launched the exploration win money
                listPlayer.getMainPlayer().perl+=currentCost
                currentCost++
                explore()
            }

            listPlayer.up()
        }
    }

    fun playerWhoLaunchedExlpoTakeAllie(cardToBuy:Allie)
    {
        //if the player take the last card
        if(listProposedCard.size== NUMBER_MAX_CARD_EXPLO_SHOW)
            listPlayer.getCurrent().perl++

        listPlayer.getCurrent().getAllie(cardToBuy)
        controller!!.explorationFinish()
    }

    fun sendToConseil():MutableList<Allie>
    {
        val cardsToSend=listProposedCard
        listProposedCard.clear()
        return cardsToSend
    }

    enum class Choice{
        BUY,
        PASS,
    }

    override fun toString(): String {
        return "=====Explo===== \nCOST =$currentCost \nBUYER : ${listPlayer.getCurrent().nom} " +
                "\nSELLER : ${listPlayer.getMainPlayer().nom}\n" +
                listProposedCard.joinToString(separator = "\n"){ it.toString()}
    }
}