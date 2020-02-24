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
abstract class ActivePermanentPower:Power,InstantEffectPower
{
    //for example if the lord is killed by assassin, this goes to false
    var isAvailable=true

    //do nothing when bought
    override fun init(player: Player, game: Game) {
        //the power is called when the player clicks on the lord
    }
}


/**these powers runs always in background and changes continuously the rules of the game**/
interface PassivePermanentPower:Power

/**All interface that represents [PassivePermanentPower]*/
interface BuyLordPrice:PassivePermanentPower
{
    /**Add modification to the old price and return the new Price**/
    fun computePrice(originPrice:Int,lord: Lord):Int
}

interface MilitaryLordAttack

