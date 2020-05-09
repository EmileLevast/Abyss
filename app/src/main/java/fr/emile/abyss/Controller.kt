package fr.emile.abyss

import fr.emile.abyss.affichage.GUIView
import fr.emile.abyss.modelClass.ConfigGame
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.FishType
import fr.emile.abyss.modelClass.gameItems.Location
import fr.emile.abyss.modelClass.gameItems.Lord

class Controller(activity:MainActivity) {

    //the config for the game
    var configGame=ConfigGame()

    //we create the game
    lateinit var game:Game
    //must be an IView in order to call its methods
    var view:GUIView= GUIView(activity)

    init {
        redirectToConfigGame()
    }

    /**Configuration Of the game**/
    fun redirectToConfigGame()
    {
        view.redirectMainMenuToConfigGameFrag()
    }

    fun createAPlayer(name:String,idImage:Int)
    {
        configGame.addAPlayer(name,idImage)
    }

    fun deleteAPlayer(nameOfPlayerToBeRemoved:String)
    {
        configGame.removePlayer(nameOfPlayerToBeRemoved)
    }

    fun resetConfigGame()
    {
        configGame.reset()
    }

    fun createTheGame()
    {
        game= Game(configGame)
        view.clearScreen()
        updateToolBar()
        view.newGameBegin()
    }

    /**Déroulement du jeu**/
    //le joueur clic sur le bouton pour passer au tour suivant
    fun playerClickToBeginNewTurn()
    {
        game.nextTurn()

        updateToolBar()
    }

    //Print a button to authorize next player to play
    fun playerFinishTurn(player:Player)
    {
        game.endTurn(player)
        view.AuthorizeNextTurn(player.nom)
    }

    fun updateToolBar()
    {
        //we change the title to print the name of the current's turn player
        view.updateActionBar( game.listPlayer.getCurrent())
    }

    /**Invocator Power**/
    fun playerClickToDoAdditionnalTurn()
    {
        game.resetActivePermanentPowerPlayer()
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
        playerFinishTurn(game.listPlayer.getCurrent())
    }

    /**Council**/
    fun takeCouncilStack(fishType: FishType)
    {
        view.clearScreen()
        game.takeCouncilStack(fishType)

        //we call next turn here because we want to only call game.takeCouncilStack(fishType)
        // for the power that can take too stack
        //game.nextTurn()
        playerFinishTurn(game.listPlayer.getCurrent())
    }

    fun launchCouncil()
    {
        view.createCouncil(game.council,game.whatToDoWithCouncil())
    }

    /**Court**/
    fun playerPayToDrawNewLord()
    {
        game.playerPayToDrawNewLord()

        //the player view may be shown on the screen too and we need to see the difference of perls
        view.updatePlayerScreen(game.listPlayer.getCurrent())
    }

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

        playerFinishTurn(player)

    }

    /**Location Stack**/
    fun playerLaunchLocationDraw()
    {
        view.createLocationStackFrag(game.locationStack)
    }

    fun playerBuyLocation(locationToBuy:Location)
    {
        MainActivity.generatorFragment!!.popLast()
        game.playerBuyLocation(locationToBuy)
    }

    fun playerDrawOtherLocations(nbrNewLocations:Int)
    {
        game.playerDrawNewLocations(nbrNewLocations)
        view.showNewAvailableLocation(game.locationStack)
    }

    /**
     * The game finish
     */
    fun gameFinished()
    {
        game.finished()
        view.createEndGameScreen(game.endGame)
    }


}