package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.modelClass.gameItems.Council
import fr.emile.abyss.modelClass.gameItems.Cour
import fr.emile.abyss.modelClass.gameItems.FishType

const val PLAYER_NUMBER=4
class Game {

    var listPlayer= Container<Player>()


    //intialized when create exploration
    var exploration:Exploration? = null
    var cour= Cour()
    var conseil=Council()

    init {

        //TODO let the user choose his name
        for(i in (65..(PLAYER_NUMBER+65)))
        {
            listPlayer.add(Player(i.toChar().toString()))
        }
    }

    /**Explorration**/


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
        conseil.addExplorationDroppedCards(exploration!!.sendToConseil())
        //we call the next player
        listPlayer.next()
    }

    /**council**/
    fun takeCouncilStack(fishType: FishType)
    {
        listPlayer.getCurrent().addAllie(conseil.takeStack(fishType))
    }



    /**Court**/

    /**when the [player] buy a lord, we take his allie cards to send it to the **/
    fun sendPlayerAllieToDiscard(player: Player)
    {
        //select card that the player chose to buy the lord and send it to the discard list
        exploration!!.sendToDiscardList(player.listAllie.filter { allie -> allie.selectedToBuyLord })

        //we take of the card to discard
        player.listAllie.removeAll { allie->allie.selectedToBuyLord }
    }

    override fun toString(): String {
        return "=====Game====="+exploration.toString()+cour.toString()
    }
}
