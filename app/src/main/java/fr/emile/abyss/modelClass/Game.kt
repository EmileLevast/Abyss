package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.R
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.*

const val PLAYER_NUMBER=3
class Game {

    var listPlayer= Container<Player>()


    //intialized when create exploration
    var exploration:Exploration? = null
    var court= Court()
    var council= Council()
    var endGame=EndGame(listPlayer)

    init {

        //TODO let the user choose his name
        //TODO interdire plusieurs joueur même nom, voir le pouvoir de l'assassin
        for(i in (65 until (PLAYER_NUMBER+65)))
        {
            listPlayer.add(Player(i.toChar().toString(), R.drawable.couverture))
        }

        cheatFirstPLayer()
    }

    /**[Exploration]**/


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

        //we retrieve the card to send to council
        val listCarToSendToCouncil=exploration!!.sendToConseil()
        val currentPlayer=listPlayer.getCurrent()
        currentPlayer.listRulesPower.
            applyToCorrespondingEvent<ExplorationSendToCouncil>(object :ExplorationSendToCouncil{},currentPlayer
            ) {it.actionAccordingTo(listCarToSendToCouncil,currentPlayer)}

        //we send all the allies to the council
        council.addExplorationDroppedCards(listCarToSendToCouncil)
        //we call the next player
        //nextTurn()
    }

    /**[Council]**/
    fun takeCouncilStack(fishType: FishType)
    {
        listPlayer.getCurrent().addAllie(council.takeStack(fishType))

        //next turn si called in the controller , see there for mor information

    }

    /**watch within the power of the player to see what to do with the council**/
    fun whatToDoWithCouncil(): (FishType) -> Unit {

        //we intialize with no action but normally if there  is no power corresponding
        //to a stack draw, the default action define in the interface CouncilStack is taken
        var actionOnStackClick:(fishtype:FishType)->Unit={}
        val player=listPlayer.getCurrent()
        player.listRulesPower.applyToCorrespondingEvent<CouncilStack>(object : CouncilStack {},player)
        { actionOnStackClick=it.getActionOnStack() }

        return actionOnStackClick
    }



    /**[Court]**/
    fun playerWantToBuyLord(lordToBuy: Lord)
    {
        court.playerWantToBuy(listPlayer.getCurrent(),lordToBuy)
    }

    /**when the [player] buy a lord, we take his allie cards to send it to the **/
    fun sendPlayerAllieToDiscard(player: Player)
    {
        //select card that the player chose to buy the lord and send it to the discard list
        exploration!!.sendToDiscardList(player.listAlly.filter { allie -> allie.selectedToBuyLord })

        //we take of the card to discard
        player.listAlly.removeAll { allie->allie.selectedToBuyLord }
    }

    /**
     * when the player bought something and his turn is finished
     */
    fun courtFinish(player: Player, lordToBuy: Lord)
    {
        sendPlayerAllieToDiscard(player)
        lordToBuy.power.init(player,this)
    }

    /**===Rule===**/
    fun nextTurn()
    {
        //on pointe le joueur suivant
        listPlayer.next()

        //on reinitialise les pouvoirs qui sont faisable une fois par tour
        listPlayer.getCurrent().listLord.forEach { if(it.power is ActivePermanentPower){it.power.isAvailable=true} }

        //on regarde si le jeu est fini
        if(endGame.isGameFinished())
        {
            controller!!.gameFinished()
        }
    }

    //things to do before going to the nextPlayer
    fun endTurn(player:Player)
    {
        player.listRulesPower.applyToCorrespondingEvent<CountCardHand>(object :CountCardHand{
            //this attribute is never used because the PassivePermanentPower redefine it in his init() function
            override var nameOfAttackingPlayer=player.nom
        },player){
            //just activate the corresponding frag that apply effects
            it.manageHandCards(player)
        }
    }


    override fun toString(): String {
        return "=====Game====="+exploration.toString()+court.toString()
    }

    //TODO remove this cheat
    private fun cheatFirstPLayer()
    {
        createExploration()
        listPlayer.getCurrent().addAllie(exploration!!.deckAllie)
        listPlayer.getCurrent().perl+=5
        //on ajoute des cartes à un autre joueur aussi
        listPlayer.listElt[1].addAllie(exploration!!.deckAllie.toMutableList())
    }
}
