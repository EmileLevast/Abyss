package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player

/**All the [Power] implements this interface**/
interface Power
{
    /**Called when the [player] buy the lord with this power**/
    fun init(player: Player,game: Game)
}

/**for power that do something to the player according to current game entries**/
interface InstantEffectPower
{
    fun activate(player: Player,game: Game)
}

/**Represents power that trigger only once at buying**/
interface InstantPower:Power,InstantEffectPower
{
    override fun init(player: Player, game: Game) {
        //when we buy the lord we immediately do his power
        activate(player,game)
    }
}

/**Implement this to describe a lord that has an instant power which affects only and all the other players**/
interface InfluenceAllOthers:InstantPower
{
    //do not override this use activateOnOtherInstead
    //otherWise don't use this is not the right interface to use
    override fun activate(player: Player, game: Game) {
        val listPlayerTargeted = mutableListOf<Player>().apply { addAll(game.listPlayer.listElt) }
            .filter { it.nom != player.nom }

        val iterListTarget = listPlayerTargeted.iterator()

        activateOnOther(iterListTarget,player)
    }

    fun activateOnOther(iterListTarget:Iterator<Player>,playerAttacking:Player)
}


/**For the power that are permanent but affect the game only when the player decides to**/
interface ActivePermanentPower:Power,InstantEffectPower
{
    //set to false when you use the power, so you c'ant' use it again in the turn
    var isAvailable:Boolean
    //do nothing when bought
    override fun init(player: Player, game: Game) {
        isAvailable=true

        //the power is called when the player clicks on the lord
    }
}


/**these powers runs always in background and changes continuously the rules of the game**/
interface PassivePermanentPower:Power
{
    fun getKeyForMap():String=this::class.java.interfaces.first().simpleName

    override fun init(player: Player, game: Game) {
        player.listRulesPower.addPower(this)
    }

    fun remove(player: Player, game: Game)
    {
        player.listRulesPower.deletePower(this)
    }
}


/**use this interface to implement passive power of military Lord**/
interface PassivePowerInfluenceOthers:PassivePermanentPower,InstantEffectPower
{
    var nameOfAttackingPlayer:String
    override fun init(player: Player, game: Game) {
        nameOfAttackingPlayer=player.nom
        //because all other players have to see it in their rules
        iterateOnlyOtherPlayers(player,game.listPlayer.listElt,RulesPower::addPower)

    }

    override fun remove(player: Player, game: Game) {
        iterateOnlyOtherPlayers(player,game.listPlayer.listElt,RulesPower::deletePower)
    }

    fun iterateOnlyOtherPlayers(player: Player,allPlayers: MutableList<Player>, transform:RulesPower.(power:PassivePermanentPower)-> Unit)
    {
        allPlayers.forEach {
            if(it!==player)
            {
                it.listRulesPower.transform(this)
            }
        }
    }

    //override this to do describe the power of the military Lord
    override fun activate(player: Player, game: Game)
}

/**All interface that represents [PassivePermanentPower]*/


//when someone purchases a lord, determine his price
interface BuyLordPrice:PassivePermanentPower
{
    /**Add modification to the old price and return the new Price**/
    fun computePrice(originPrice:Int):Int {
        return originPrice
    }
}


/**when someone is under attack from military Lord**/
interface MilitaryLordAttack:PassivePermanentPower
{
    fun isAttackAvailable()=true
}

interface BoughtLordFederateAllie:PassivePermanentPower
{
    fun federateAllie(player: Player, listAllyUsedToBuy:List<Ally>)
    {
        //federate allies
        val federatedAllie=listAllyUsedToBuy.minBy { allieBuying->allieBuying.number}

        //on ajoute l'allie a la liste d'allie federe
        player.listAllieFedere.add(federatedAllie!!)
    }
}


//which color is used to buy the lord and is it the right color.
interface BuyLordColorAllie:PassivePermanentPower
{
    fun isAuthorizedToBuy(listDifferentTypeUseForBuy: List<FishType>,lordToBuy:Lord):Boolean
    {
        return listDifferentTypeUseForBuy.contains(lordToBuy.obligedType)
    }
}


//What to do when you launch a council
//can you take too stack or something else
//which color is used to buy the lord and is it the right color.
interface CouncilStack:PassivePermanentPower
{
    fun getActionOnStack():(fishtype:FishType)->Unit
    {
        return {fishtype: FishType ->  controller!!.takeCouncilStack(fishtype)}
    }
}


//when a player finish an exploration, does he win something
//(armateur give one pearl for eachcolor allie send to council)
interface ExplorationSendToCouncil:PassivePermanentPower
{
    //what the player can win thanks to this exploration
    //Armateur ;)
    //par defaut il se passe rien
    fun actionAccordingTo(listCardSendToCouncil:MutableList<Ally>,player:Player)=Unit
}

/**Implement this interface to define something to do with the player according to the number of cards in his hands
 *Think about the amree chief, he has 2 power, firstly instantly every one must discard, and secondly at each turn.
 * */

interface CountCardHand: InfluenceAllOthers,PassivePowerInfluenceOthers
{
    override fun init(player: Player, game: Game) {
        //one side we add it to the permanent power
        super<PassivePowerInfluenceOthers>.init(player, game)
        //second side we execute the power for the first time
        super<InfluenceAllOthers>.init(player, game)
    }

    //implement this to design the thing immediately done after buying
    override fun activate(player: Player, game: Game) {
        super<InfluenceAllOthers>.activate(player, game)
    }

    override fun activateOnOther(iterListTarget: Iterator<Player>, playerAttacking: Player)

    //implement this to design the action to do at each turn
    fun manageHandCards(player:Player)=Unit
}
