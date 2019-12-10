package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.modelClass.gameItems.Deck

const val PLAYER_NUMBER=4
class Game {

    var listPlayer= Container<Player>()
    var deck= Deck()

    //intialized when create exploration
    var exploration:Exploration? = null

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
            exploration=Exploration(listPlayer,deck.stackAllie)
        }else
        {
            exploration!!.initializeExplo(listPlayer,deck.stackAllie)
        }

        exploration?.explore()
    }

    override fun toString(): String {
        return "Game:$deck"
    }


}