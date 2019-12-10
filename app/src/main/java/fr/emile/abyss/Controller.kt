package fr.emile.abyss

import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class Controller {

    var game =Game()
    var view= View()

    fun updateView()
    {
        view.showGame(game)
    }

    fun updateEploration()
    {
        view.showExploration(game.exploration!!)
    }

    fun playerLaunchExploration()
    {
        game.createExploration()
    }

    //called when the player decided to buy or not explorationCard
    fun playerBuyOrNotExploCard(choice:Exploration.Choice)
    {
        game.exploration?.playerMakeChoice(choice)
    }
}