package fr.emile.abyss

import fr.emile.abyss.affichage.GUIView
import fr.emile.abyss.affichage.IView
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class Controller(activity:MainActivity) {

    //we create the game
    var game =Game()
    //must be an IView in order to call its methods
    var view:IView= GUIView(activity)


    fun playerLaunchExploration()
    {
        game.createExploration()

        //we can scpecify exploration is not null because we call createExploration on game object just above
        view.createExploration(game.exploration!!)
    }

    //called when the player click on buy or not from UI
    fun playerBuyOrNotExploCard(choice:Exploration.Choice)
    {
        game.exploration?.playerMakeChoice(choice)
    }

    fun updateExplorationView()
    {
        view.updateExploration(game.exploration!!)
    }

    fun explorationFinish()
    {
        game.explorationFinish()
    }
}