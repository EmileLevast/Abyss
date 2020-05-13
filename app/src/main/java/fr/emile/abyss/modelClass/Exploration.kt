package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.ContainerPlayerExplo
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.Ally
import fr.emile.abyss.modelClass.gameItems.Deck

const val NUMBER_MAX_CARD_EXPLO_SHOW=5

class Exploration (listPlayer:Container<Player>){

    lateinit var listPlayer: ContainerPlayerExplo

    //created one time, same for all explorations because we keep the same object Exploration during all the game
    var deckAllie=Deck().stackAlly

    //register all the visible cards
    var listProposedCard=mutableListOf<Ally>()

    var listDiscard=mutableListOf<Ally>()

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
        if(deckAllie.isEmpty())//si il reste encore des cartes a tirer
        {
            deckAllie.addAll(listDiscard.shuffled())

            //apres avoir donne des nouvelles cartes on vide la pioche de defausse
            listDiscard.clear()
        }

        listProposedCard.add(deckAllie.removeAt(0))
        listPlayer.reset()
    }

    fun playerMakeChoice(choice:Choice)
    {

        if(listPlayer.isMainPlayer())
        {
            //si le dernier joueur prend la carte ou qu'il est a la fin
            if(choice==Choice.BUY||listProposedCard.size== NUMBER_MAX_CARD_EXPLO_SHOW)
            {
                playerWhoLaunchedExlpoTakeAllie()
                //this is the end, on arrete
                return
            }else
            {
                //si le dernier joueur prend pas on decouvre la suite
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
            }else
            {
                listPlayer.up()
            }

        }

        //we update the view
        controller?.updateExplorationView()
    }

    /**
     * This function call the end of the exploration
     */
    fun playerWhoLaunchedExlpoTakeAllie()
    {
        //if the player take the last card
        if(listProposedCard.size== NUMBER_MAX_CARD_EXPLO_SHOW)
            //so he wins 1 perl
            listPlayer.getCurrent().perl++

        val cardToBuy:Ally=listProposedCard.removeAt(listProposedCard.size-1)

        listPlayer.getCurrent().addAllie(cardToBuy)
        controller!!.explorationFinish()
    }

    fun sendToConseil():MutableList<Ally>
    {
        return listProposedCard
    }

    /**add [listAllyToDiscard] to the [listDiscard], doesn't create new references**/
    fun sendToDiscardList(listAllyToDiscard:List<Ally>)
    {
        listDiscard.addAll(listAllyToDiscard)
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