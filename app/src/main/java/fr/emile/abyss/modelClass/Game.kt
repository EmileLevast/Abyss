package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.*

class Game(configGame: ConfigGame) {

    var listPlayer= Container<Player>()


    //intialized when create exploration
    var exploration:Exploration? = null
    var court= Court()
    var council= Council()
    var locationStack=LocationStack()
    var endGame=EndGame(listPlayer)

    init {

        //on ajoute tous les joueurs prepares
        configGame.listPlayersToAddToGame.forEach { listPlayer.add(it) }

        cheatFirstPlayer()
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
        addStackToPlayerAndHandleCouncil(fishType)

        //next turn si called in the controller , see there for mor information

        val player=listPlayer.getCurrent()
        player.listRulesPower.applyToCorrespondingEvent<OnCouncilStackTaken>(object : OnCouncilStackTaken {},player)
        { it.actionOnCouncilStackTaken(this,player) }
    }

    /**Special use for the ALchimiste, otherwise his power will be called each time in [takeCouncilStack]**/
    fun addStackToPlayerAndHandleCouncil(fishType: FishType)
    {
        listPlayer.getCurrent().addAllie(council.takeStack(fishType))
    }

    /**Oracle Power**/
    fun sendStackToDiscard(fishType: FishType)
    {
        exploration!!.sendToDiscardList(council.takeStack(fishType))
    }


    /**[Court]**/
    fun playerPayToDrawNewLord()
    {
        court.playerPayToDrawOneLord(listPlayer.getCurrent())
    }

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

    /**Location**/
    fun playerBuyLocation(locationToBuy:Location)
    {
        listPlayer.getCurrent().buyLocation(locationToBuy)
        locationStack.locationBought(locationToBuy)
    }

    fun playerDrawNewLocations(nbrNewLocationsWanted:Int)
    {
        locationStack.drawNewLocation(nbrNewLocationsWanted)
    }

    /**===Rule===**/
    fun nextTurn()
    {
        //on pointe le joueur suivant
        listPlayer.next()

        //on reinitialise les pouvoirs qui sont faisable une fois par tour
        resetActivePermanentPowerPlayer()

        //on regarde si le jeu est fini
        if(listPlayer.getCurrent().hasMaxNbrLord())
        {
            controller!!.gameFinished()
        }
    }

    fun resetActivePermanentPowerPlayer()
    {
        listPlayer.getCurrent().listLord.forEach { if(it.power is ActivePermanentPower){it.power.isAvailable=true} }
    }

    //things to do before going to the nextPlayer
    fun endTurn(player:Player)
    {
        player.watchForBuyLocation()


        //power according bonuses to player each turn
        player.listRulesPower.applyToCorrespondingEvent<EndTurnPower>(object : EndTurnPower{},player)
        {
            it.bonusToThePlayer(player)
        }

        //Power linked to the cards in hands
        player.listRulesPower.applyToCorrespondingEvent<CountCardHand>(object :CountCardHand{
            //this attribute is never used because the PassivePermanentPower redefine it in his init() function
            override var nameOfAttackingPlayer=player.nom
        },player){
            //just activate the corresponding frag that apply effects
            it.manageHandCards(player)
        }
    }

    fun finished()
    {
        endGame.computeAllPlayerInfluencePoint()
    }


    override fun toString(): String {
        return "=====Game====="+exploration.toString()+court.toString()
    }

    //TODO remove this cheat
    private fun cheatFirstPlayer()
    {
        createExploration()
        listPlayer.getCurrent().addAllie(exploration!!.deckAllie)
        listPlayer.getCurrent().perl+=5
        listPlayer.getCurrent().nbrKeyToken+=9
        //on ajoute des cartes à un autre joueur aussi
        listPlayer.listElt[1].addAllie(exploration!!.deckAllie.toMutableList())
    }
}
