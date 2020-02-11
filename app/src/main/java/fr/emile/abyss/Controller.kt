package fr.emile.abyss

import fr.emile.abyss.affichage.GUIView
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.FishType

class Controller(activity:MainActivity) {

    //we create the game
    var game =Game()
    //must be an IView in order to call its methods
    var view:GUIView= GUIView(activity)


    /**Exploration**/

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

        //we take off fragment
        view.clearScreen()
    }

    /**Council**/

    fun takeCouncilStack(fishType: FishType)
    {
        game.takeCouncilStack(fishType)
        view.clearScreen()
    }

    fun launchCouncil()
    {
        view.createCouncil(game.conseil)
    }

    /**Court**/
    fun courtFinish(playerBought:Boolean,player: Player)
    {
        //si le joueur a acheté  alors on enleve ses allies qui ont servi à acheter
        if(playerBought)
        {
            game.sendPlayerAllieToDiscard(player)
        }

        view.clearScreen()
    }

}