package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.modelClass.gameItems.Conseil
import fr.emile.abyss.modelClass.gameItems.Cour
import fr.emile.abyss.modelClass.gameItems.Deck

const val PLAYER_NUMBER=4
class Game {

    var listPlayer= Container<Player>()


    //intialized when create exploration
    var exploration:Exploration? = null
    var cour= Cour()
    var conseil=Conseil()

    init {

        //TODO let the user choose his name
        for(i in (65..(PLAYER_NUMBER+65)))
        {
            listPlayer.add(Player(i.toChar().toString()))
        }
    }

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

    override fun toString(): String {
        return "=====Game====="+exploration.toString()+cour.toString()
    }

    fun explorationFinish() {
        //we send all the allies to the council
        conseil.addExplorationDroppedCards(exploration!!.sendToConseil())
        //we call the next player
        listPlayer.next()
    }


}