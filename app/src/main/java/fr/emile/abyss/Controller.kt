package fr.emile.abyss

import fr.emile.abyss.Affichage.ConsoleView
import fr.emile.abyss.Affichage.IView
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class Controller {

    var game =Game()
    //must be an IView in order to call its methods
    var view:IView= ConsoleView()

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

    fun explorationFinish()
    {
        game.explorationFinish()
    }
}