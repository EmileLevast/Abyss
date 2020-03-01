package fr.emile.abyss.modelClass.gameItems

import android.util.Log
import fr.emile.abyss.R
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player

class Lord (var FishType: FishType, var name:String, var imgId:Int, var price:Int,
            var numberAllieType:Int, var obligedType:FishType, var influencePoint:Int,
            val power: Power) {

    override fun toString(): String {
        return "Lord(FishType=$FishType, name='$name', influence Point='$influencePoint', imgId=$imgId, price=$price, numberAllieType=$numberAllieType, obligedType=$obligedType)"
    }

    companion object {

        //TODO delete all mocked power
        private val mockedInstantPower=object : InstantPower
        {
            override fun activate(player: Player, game: Game) {
                Log.w("msg","InstantPower mocked activated")
            }
        }
        private val mockedActivePermanentPower= object : ActivePermanentPower {
            override fun activate(player: Player, game: Game) {
                Log.w("msg","ActivePermanentPower mocked activated")
            }
        }
        private val mockedPassivePermanentPower=object : MilitaryLordAttack{
            override fun isAttackAvailable():Boolean
            {
                Log.w("msg","MilitaryLordAttack mocked activated")
                return false
            }
        }

        //order of implemented interfaces really important,choose the receiver for the function init and remove
        private val mockedPassivePermanentInfluencePower=object : PassivePowerInfluenceOthers,MilitaryLordAttack{
            override fun isAttackAvailable():Boolean
            {
                Log.w("msg","PassivePowerInfluenceOthers mocked activated")
                return false
            }
        }


        //Lord armateur with his power
        val listLord= mutableListOf(Lord(FishType.SEA_SHELL,"l'Armateur", R.drawable.armateur,6,3,FishType.CRAB,6,
            object : ActivePermanentPower{
                override fun activate(player: Player, game: Game) {
                    player.perl+=1
                }
            }),
            Lord(FishType.CRAB,"The highway man", R.drawable.armateur,12,3,FishType.CRAB,8, mockedInstantPower),
            Lord(FishType.JELLYFISH,"The highway man", R.drawable.armateur,12,3,FishType.CRAB,8,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"le chef des armées", R.drawable.armateur,12,3,FishType.CRAB,8,mockedPassivePermanentPower),
            Lord(FishType.OCTOPUS,"le geolier", R.drawable.armateur,12,3,FishType.CRAB,8,mockedPassivePermanentInfluencePower),
            Lord(FishType.SEA_HORSE,"The highway man", R.drawable.armateur,12,3,FishType.CRAB,8,mockedPassivePermanentInfluencePower),
            Lord(FishType.JELLYFISH,"le chef des armées", R.drawable.armateur,12,3,FishType.CRAB,8,mockedPassivePermanentPower),
            Lord(FishType.SEA_SHELL,"le geolier", R.drawable.armateur,12,3,FishType.CRAB,8,mockedPassivePermanentPower)
            ,Lord(FishType.CRAB,"The highway man", R.drawable.armateur,12,3,FishType.CRAB,8,mockedInstantPower),
            Lord(FishType.CRAB,"le chef des armées", R.drawable.armateur,12,3,FishType.CRAB,8,mockedActivePermanentPower))
    }
}