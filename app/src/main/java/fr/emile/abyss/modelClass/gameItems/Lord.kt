package fr.emile.abyss.modelClass.gameItems

import android.util.Log
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player

class Lord (var FishType: FishType, var name:String,var hasKey:Boolean, override var imgId:Int, var price:Int,
            var numberAllieType:Int, var obligedType:FishType?, var influencePoint:Int,
            val power: Power) :IShowImage{

    //if the lord has been targeted buy the assassin
    var isAlive:Boolean=true
    private set

    //if the lord is free or if he has already used his key to buy a place
    var isFree:Boolean=true
    private set

    fun die(){
        isAlive=false
    }

    fun useToBuyLocation()
    {
        isFree=false
    }

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
        //use this noPower to implement Farmer Lord
        private val noPower= object : InstantPower {
            override fun activate(player: Player, game: Game) {
            }
        }
        private val mockedPassivePermanentPower=object : MilitaryLordAttack{
            override fun isAttackAvailable():Boolean
            {
                Log.w("msg","MilitaryLordAttack mocked activated")
                return false
            }
        }




        //Lord armateur with his power
        val listLord= mutableListOf(
            Lord(FishType.JELLYFISH,"l'Alchimiste",true, R.drawable.alchimiste,7,1,FishType.JELLYFISH,5,
            object : CouncilStack{
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

            Lord(FishType.AMBASSADOR,"L'Ancien",false, R.drawable.ancien,10,5,null,3, mockedInstantPower),

            Lord(FishType.JELLYFISH,"L'Apprenti",false, R.drawable.apprenti,6,3,FishType.JELLYFISH,9,
                object:InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //we launch a council with the default action
                        controller!!.view.createCouncil(game.council) {
                            //we call the normal function to
                            controller!!.takeCouncilStack(it)
                        }
                    }
                }),

            Lord(FishType.SEA_HORSE,"L'Aquaculteur",false, R.drawable.aquaculteur,9,3,FishType.SEA_HORSE,11, noPower),

            Lord(FishType.SEA_SHELL,"L'Armateur",true, R.drawable.armateur,6,3,FishType.SEA_SHELL,6,
                object : explorationSendToCouncil{
                    override fun actionAccordingTo(listCardSendToCouncil: MutableList<Ally>, player: Player) {
                        //on compte le nombre de type différents dans la liste
                        //et on l'ajoute au total de perl
                        player.perl+=listCardSendToCouncil.map{allie -> allie.type }.distinct().size
                    }
                }),

            Lord(FishType.CRAB,"L'Assassin",false, R.drawable.assassin,10,1,FishType.CRAB,6,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //on recupere tous les joueurs et on enleve le joueur qui assassine
                        val listPlayerTargeted= mutableListOf<Player>().apply{addAll(game.listPlayer.listElt)}.filter{it.nom!=player.nom}

                        val iterListTarget=listPlayerTargeted.iterator()

                        //what to do when you finish an assassin frag
                        fun actionOnClick()
                        {
                            //tant qu'il y a des personnages
                            if (iterListTarget.hasNext()) {
                                val playerAttacked=iterListTarget.next()

                                //on lance l'evenement attackmilitarylord
                                //comme ça le joueur n'est pas attaque s'il a la chamanesse
                                playerAttacked.playerUnderAttackMilitaryLord {_,_->

                                    //on verifie qu'il y a des seigneurs a tuer
                                    val listFreeLordPlayer=playerAttacked.listLord.filter { it.isFree && it.isAlive }
                                    if (!listFreeLordPlayer.isEmpty()) {
                                        //on créé le frag pour assassiner
                                        controller!!.view.createPowerLordFrag(
                                            listFreeLordPlayer,
                                            "${player.nom} is using Assassin\n${playerAttacked.nom} choose a lord to sacrifice",
                                            R.drawable.assassin,
                                            ::createViewHolderImageOnly)
                                        { lord ->
                                            lord.die()
                                            //on suppr le fragment assassin actuel
                                            MainActivity.generatorFragment!!.popLast()
                                            actionOnClick()
                                        }
                                    }
                                }
                            }
                        }

                        //we call the first assassin frag
                        actionOnClick()

                    }
                }),
            Lord(FishType.AMBASSADOR,"L'Ermite",false, R.drawable.ermite,10,5,null,5,mockedPassivePermanentPower),
            Lord(FishType.SEA_SHELL,"L'Esclavagiste",true, R.drawable.esclavagiste,8,1,FishType.SEA_SHELL,5,mockedPassivePermanentPower),
            Lord(FishType.SEA_HORSE,"L'Exploitant",false, R.drawable.exploitant,10,1,FishType.SEA_HORSE,12,mockedInstantPower),
            Lord(FishType.JELLYFISH,"L'Illusionniste",false, R.drawable.illusionniste,10,1,FishType.JELLYFISH,9,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"L'Invocateur",false, R.drawable.invocateur,8,1,FishType.JELLYFISH,8,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"L'Intriguant",false, R.drawable.l_intriguant,8,3,FishType.OCTOPUS,6,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"La Bergère",true, R.drawable.la_bergere,8,1,FishType.SEA_HORSE,6,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"La Chamanesse",true, R.drawable.la_chamanesse,6,3,FishType.JELLYFISH,5,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"La Gardienne",true, R.drawable.la_gardienne,6,3,FishType.SEA_HORSE,6,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"La Negociante",false, R.drawable.la_negociante,10,3,FishType.SEA_SHELL,9,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"Le Boutiquier",false, R.drawable.le_boutiquier,6,3,FishType.SEA_SHELL,9,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Chasseur",false, R.drawable.le_chasseur,8,2,FishType.CRAB,6,mockedActivePermanentPower),
            Lord(FishType.CRAB,"le chef des armées",true, R.drawable.le_chef_des_armees,8,1,FishType.CRAB,4,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"Le Colporteur",false, R.drawable.le_colporteur,8,1,FishType.SEA_SHELL,9,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Corrupteur",false, R.drawable.le_corrupteur,10,1,FishType.OCTOPUS,6,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Diplomate",true, R.drawable.le_diplomate,8,1,FishType.OCTOPUS,5,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"Le Faucheur",true, R.drawable.le_faucheur,7,2,FishType.SEA_HORSE,6,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Geôlier",false, R.drawable.le_geolier,6,3,FishType.CRAB,7,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"Le Maître de magie",true, R.drawable.le_maitre_de_magie,10,3,FishType.JELLYFISH,6,mockedActivePermanentPower),
            Lord(FishType.SEA_HORSE,"Le Meunier",false, R.drawable.le_meunier,8,2,FishType.SEA_HORSE,10,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Questeur",false, R.drawable.le_questeur,7,2,FishType.CRAB,7,mockedActivePermanentPower),
            Lord(FishType.CRAB,"Le Recruteur",true, R.drawable.le_recruteur,10,2,FishType.CRAB,4,mockedActivePermanentPower),
            Lord(FishType.SEA_SHELL,"Le Rentier",true, R.drawable.le_rentier,10,2,FishType.SEA_SHELL,5,mockedActivePermanentPower),
            Lord(FishType.AMBASSADOR,"Le Sage",false, R.drawable.le_sage,10,5,null,4,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Traitre",false, R.drawable.le_traitre,12,3,FishType.OCTOPUS,6,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Trésorier",true, R.drawable.le_tresorier,10,2,FishType.OCTOPUS,5,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"L' Opportuniste",true, R.drawable.opportuniste,6,3,FishType.OCTOPUS,5,mockedActivePermanentPower),
            Lord(FishType.JELLYFISH,"L'Oracle",true, R.drawable.oracle,8,2,FishType.JELLYFISH,5,mockedActivePermanentPower)
        )
    }
}