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


    /**Déroulement du jeu**/

    //le joueur clic sur le bouton pour passer au tour suivant
    fun playerClickToBeginNewTurn(activity: MainActivity)
    {
        game.nextTurn()
        //we change the title to print the name of the current's turn player
        activity.supportActionBar!!.title = game.listPlayer.getCurrent().nom

    }

    //Print a button to authorize next player to play
    fun playerFinishTurn(playerName:String)
    {
        view.AuthorizeNextTurn(playerName)
    }


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
        view.clearScreen()

        game.explorationFinish()

        //we take off fragment
        playerFinishTurn(game.listPlayer.getCurrent().nom)
    }

    /**Council**/
    fun takeCouncilStack(fishType: FishType)
    {
        view.clearScreen()
        game.takeCouncilStack(fishType)

        //we call next turn here because we want to only call game.takeCouncilStack(fishType)
        // for the power that can take too stack
        //game.nextTurn()
        playerFinishTurn(game.listPlayer.getCurrent().nom)
    }

    fun launchCouncil()
    {
        view.createCouncil(game.council,game.whatToDoWithCouncil())
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

    fun courtFinish(player: Player, lordToBuy: Lord)
    {
        view.clearScreen()
        game.courtFinish(player,lordToBuy)
        playerFinishTurn(player.nom)
    }

    /**
     * The game finish
     */
    fun gameFinished()
    {
        view.createEndGameScreen(game.endGame)
    }

}