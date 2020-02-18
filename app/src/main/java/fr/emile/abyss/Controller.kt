package fr.emile.abyss

import fr.emile.abyss.affichage.GUIView
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.FishType
import fr.emile.abyss.modelClass.gameItems.Lord

class Controller(activity:MainActivity) {

    //we create the game
    var game =Game()
    //must be an IView in order to call its methods
    var view:GUIView= GUIView(activity)


    /**Exploration**/

    fun launchExploration()
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
        view.createCouncil(game.council)
    }

    /**Court**/
    fun LaunchCourt()
    {
        view.createCourt(game.court)
    }

    fun playerWantToBuyLord(lordClicked: Lord)
    {
        game.playerWantToBuyLord(lordClicked)
    }

    fun courtFinish(player: Player)
    {
        game.courtFinish(player)
        view.clearScreen()
    }

    /**
     * The game finish
     */
    fun gameFinished()
    {
        view.createEndGameScreen(game.endGame)
    }

}