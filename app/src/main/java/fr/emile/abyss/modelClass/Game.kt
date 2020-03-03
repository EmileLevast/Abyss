package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.Council
import fr.emile.abyss.modelClass.gameItems.Court
import fr.emile.abyss.modelClass.gameItems.FishType
import fr.emile.abyss.modelClass.gameItems.Lord

const val PLAYER_NUMBER=4
class Game {

    var listPlayer= Container<Player>()


    //intialized when create exploration
    var exploration:Exploration? = null
    var court= Court()
    var council=Council()
    var endGame=EndGame(listPlayer)

    init {

        //TODO let the user choose his name
        for(i in (65..(PLAYER_NUMBER+65)))
        {
            listPlayer.add(Player(i.toChar().toString()))
        }
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
        nextTurn()
    }

    /**[Council]**/
    fun takeCouncilStack(fishType: FishType)
    {
        listPlayer.getCurrent().addAllie(council.takeStack(fishType))

        nextTurn()
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
    fun courtFinish(player: Player)
    {
        sendPlayerAllieToDiscard(player)
        nextTurn()
    }

    /**===Rule===**/
    private fun nextTurn()
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
}
