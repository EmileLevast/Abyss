package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.FishType
import fr.emile.abyss.modelClass.gameItems.Lord
import fr.emile.abyss.modelClass.gameItems.CouncilStack

const val PLAYER_NUMBER=3
class Game {

    var listPlayer= Container<Player>()


    //intialized when create exploration
    var exploration:Exploration? = null
    var court= Court()
    var council= Council()
    var endGame=EndGame(listPlayer)

    init {

        //TODO let the user choose his name
        for(i in (65 until (PLAYER_NUMBER+65)))
        {
            listPlayer.add(Player(i.toChar().toString()))
        }

        cheatFirstPLayer()
    }

    /**[Exploration]**/


    fun createExploration()
    {
        if(exploration==null)
        {
            exploration=Exploration(listPlayer)
        }else
        {
            exploration!!.initializeExplo(listPlayer)
        }

        exploration?.explore()
    }

    fun explorationFinish() {
        //we send all the allies to the council
        council.addExplorationDroppedCards(exploration!!.sendToConseil())
        //we call the next player
        //nextTurn()
    }

    /**[Council]**/
    fun takeCouncilStack(fishType: FishType)
    {
        listPlayer.getCurrent().addAllie(council.takeStack(fishType))

        //next turn si called in the controller , see there for mor information

    }

    /**watch within the power of the player to see what to do with the council**/
    fun whatToDoWithCouncil(): (FishType) -> Unit {

        //we intialize with no action but normally if there  is no power corresponding
        //to a stack draw, the default action define in the interface CouncilStack is taken
        var actionOnStackClick:(fishtype:FishType)->Unit={}
        val player=listPlayer.getCurrent()
        player.listRulesPower.applyToCorrespondingEvent<CouncilStack>(object : CouncilStack {},player)
        { actionOnStackClick=it.getActionOnStack() }

        return actionOnStackClick
    }



    /**[Court]**/
    fun playerWantToBuyLord(lordToBuy: Lord)
    {
        court.playerWantToBuy(listPlayer.getCurrent(),lordToBuy)
    }

    /**when the [player] buy a lord, we take his allie cards to send it to the **/
    fun sendPlayerAllieToDiscard(player: Player)
    {
        //select card that the player chose to buy the lord and send it to the discard list
        exploration!!.sendToDiscardList(player.listAlly.filter { allie -> allie.selectedToBuyLord })

        //we take of the card to discard
        player.listAlly.removeAll { allie->allie.selectedToBuyLord }
    }

    /**
     * when the player bought something and his turn is finished
     */
    fun courtFinish(player: Player, lordToBuy: Lord)
    {
        sendPlayerAllieToDiscard(player)
        lordToBuy.power.init(player,this)
        //nextTurn()
    }

    /**===Rule===**/
    fun nextTurn()
    {
        //on pointe le joueur suivant
        listPlayer.next()

        //on regarde si le jeu est fini
        if(endGame.isGameFinished())
        {
            controller!!.gameFinished()
        }
    }


    override fun toString(): String {
        return "=====Game====="+exploration.toString()+court.toString()
    }

    //TODO remove this cheat
    private fun cheatFirstPLayer()
    {
        createExploration()
        listPlayer.getCurrent().addAllie(exploration!!.deckAllie)
    }
}
