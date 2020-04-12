package fr.emile.abyss.modelClass.gameItems

import android.util.Log
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player

class Lord (var FishType: FishType, var name:String, override var imgId:Int, var price:Int,
            var numberAllieType:Int, var obligedType:FishType?, var influencePoint:Int,
            val power: Power) :IShowImage{

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
        val listLord= mutableListOf(Lord(FishType.JELLYFISH,"l'Alchimiste", R.drawable.alchimiste,7,1,FishType.JELLYFISH,5,
            object : councilStack{
                override fun getActionOnStack(): (fishtype: FishType) -> Unit {
                    return {
                        //we just add the stack to the player hand
                        controller!!.game.takeCouncilStack(it)
                        //we detroy the frag
                        controller!!.view.clearScreen()
                        //and we create again the council to take the second stack
                        controller!!.view.createCouncil(controller!!.game.council)
                    }
                }
            }),
            Lord(FishType.AMBASSADOR,"L'Ancien", R.drawable.ancien,10,5,null,3, mockedInstantPower),
            Lord(FishType.JELLYFISH,"L'Apprenti", R.drawable.apprenti,6,3,FishType.JELLYFISH,9,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"L'Aquaculteur", R.drawable.aquaculteur,9,3,FishType.SEA_HORSE,11,mockedPassivePermanentPower),
            Lord(FishType.SEA_SHELL,"L'Armateur", R.drawable.armateur,6,3,FishType.SEA_SHELL,6,mockedPassivePermanentInfluencePower),
            Lord(FishType.CRAB,"L'Assassin", R.drawable.assassin,10,1,FishType.CRAB,6,mockedPassivePermanentInfluencePower),
            Lord(FishType.AMBASSADOR,"L'Ermite", R.drawable.ermite,10,5,null,5,mockedPassivePermanentPower),
            Lord(FishType.SEA_SHELL,"L'Esclavagiste", R.drawable.esclavagiste,8,1,FishType.SEA_SHELL,5,mockedPassivePermanentPower),
            Lord(FishType.SEA_HORSE,"L'Exploitant", R.drawable.exploitant,10,1,FishType.SEA_HORSE,12,mockedInstantPower),
            Lord(FishType.JELLYFISH,"L'Illusionniste", R.drawable.illusionniste,10,1,FishType.JELLYFISH,9,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"L'Invocateur", R.drawable.invocateur,8,1,FishType.JELLYFISH,8,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"L'Intriguant", R.drawable.l_intriguant,8,3,FishType.OCTOPUS,6,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"La Bergère", R.drawable.la_bergere,8,1,FishType.SEA_HORSE,6,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"La Chamanesse", R.drawable.la_chamanesse,6,3,FishType.JELLYFISH,5,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"La Gardienne", R.drawable.la_gardienne,6,3,FishType.SEA_HORSE,6,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"La Negociante", R.drawable.la_negociante,10,3,FishType.SEA_SHELL,9,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"Le Boutiquier", R.drawable.le_boutiquier,6,3,FishType.SEA_SHELL,9,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Chasseur", R.drawable.le_chasseur,8,2,FishType.CRAB,6,mockedActivePermanentPower),
            Lord(FishType.CRAB,"le chef des armées", R.drawable.le_chef_des_armees,8,1,FishType.CRAB,4,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"Le Colporteur", R.drawable.le_colporteur,8,1,FishType.SEA_SHELL,9,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Corrupteur", R.drawable.le_corrupteur,10,1,FishType.OCTOPUS,6,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Diplomate", R.drawable.le_diplomate,8,1,FishType.OCTOPUS,5,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"Le Faucheur", R.drawable.le_faucheur,7,2,FishType.SEA_HORSE,6,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Geôlier", R.drawable.le_geolier,6,3,FishType.CRAB,7,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"Le Maître de magie", R.drawable.le_maitre_de_magie,10,3,FishType.JELLYFISH,6,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"Le Meunier", R.drawable.le_meunier,8,2,FishType.SEA_HORSE,10,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Questeur", R.drawable.le_questeur,7,2,FishType.CRAB,7,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Recruteur", R.drawable.le_recruteur,10,2,FishType.CRAB,4,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"Le Rentier", R.drawable.le_rentier,10,2,FishType.SEA_SHELL,5,mockedActivePermanentPower),
            Lord(FishType.AMBASSADOR,"Le Sage", R.drawable.le_sage,10,5,null,4,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Traitre", R.drawable.le_traitre,12,3,FishType.OCTOPUS,6,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Trésorier", R.drawable.le_tresorier,10,2,FishType.OCTOPUS,5,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"L' Opportuniste", R.drawable.opportuniste,6,3,FishType.OCTOPUS,5,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"L'Oracle", R.drawable.oracle,8,2,FishType.JELLYFISH,5,mockedActivePermanentPower)
        )
    }
}