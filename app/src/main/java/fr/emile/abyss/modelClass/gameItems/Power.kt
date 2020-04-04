package fr.emile.abyss.modelClass.gameItems

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


/**For the power that are permanent but affect the game only when the player decides to**/
interface ActivePermanentPower:Power,InstantEffectPower
{
    //do nothing when bought
    override fun init(player: Player, game: Game) {
        //the power is called when the player clicks on the lord
    }
}


/**these powers runs always in background and changes continuously the rules of the game**/
interface PassivePermanentPower:Power
{
    fun getKeyForMap():String=this::class.java.simpleName

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
    override fun init(player: Player, game: Game) {
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
    override fun activate(player: Player, game: Game) {}
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

interface BuyLordColorAllie:PassivePermanentPower
{
    fun isAuthorizedToBuy(listDifferentTypeUseForBuy: List<FishType>,lordToBuy:Lord):Boolean
    {
        return listDifferentTypeUseForBuy.size>=lordToBuy.numberAllieType &&
                listDifferentTypeUseForBuy.contains(lordToBuy.obligedType)
    }
}


