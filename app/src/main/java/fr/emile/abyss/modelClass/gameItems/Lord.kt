package fr.emile.abyss.modelClass.gameItems

import android.util.Log
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderAllyCheckBox
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderLord
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderPlayer
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

    fun die(playerOwner:Player){
        isAlive=false
        removePower(playerOwner)
    }

    fun useToBuyLocation(playerOwner:Player)
    {
        isFree=false
        removePower(playerOwner)
    }

    /**Only if it's a pemranent power we have to remove it from the [playerOwner].listRulespower**/
    fun removePower(playerOwner:Player)
    {
        if(power is PassivePermanentPower)
        {
            power.remove(playerOwner, controller!!.game)
        }
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
            override var isAvailable:Boolean=true
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
                object : ExplorationSendToCouncil{
                    override fun actionAccordingTo(listCardSendToCouncil: MutableList<Ally>, player: Player) {
                        //on compte le nombre de type différents dans la liste
                        //et on l'ajoute au total de perl
                        player.perl+=listCardSendToCouncil.map{allie -> allie.type }.distinct().size
                    }
                }),

            Lord(FishType.CRAB,"L'Assassin",false, R.drawable.assassin,10,1,FishType.CRAB,6,
                object : InfluenceAllOthers{
                    override fun activateOnOther(iterListTarget:Iterator<Player>,playerAttacking:Player) {

                        //what to do when you finish an assassin frag
                        fun attackNextPlayer()
                        {
                            //tant qu'il y a des personnages
                            if (iterListTarget.hasNext()) {
                                val playerAttacked=iterListTarget.next()

                                //on lance l'evenement attackmilitarylord
                                //comme ça le joueur n'est pas attaque s'il a la chamanesse
                                playerAttacked.playerUnderAttackMilitaryLord ({_,_->

                                    //on verifie qu'il y a des seigneurs a tuer
                                    val listFreeLordPlayer=playerAttacked.listLord.filter { it.isFree && it.isAlive }
                                    if (!listFreeLordPlayer.isEmpty()) {
                                        //on créé le frag pour assassiner
                                        controller!!.view.createPowerLordFrag(
                                            listFreeLordPlayer,
                                            "${playerAttacking.nom} is using Assassin\n${playerAttacked.nom} choose a lord to sacrifice",
                                            R.drawable.assassin,
                                            ::createViewHolderImageOnly,
                                        { lord ->
                                            playerAttacked.lordIsKilled(lord)
                                            //on suppr le fragment assassin actuel
                                            MainActivity.generatorFragment!!.popLast()
                                            attackNextPlayer()
                                        })
                                    }
                                },::attackNextPlayer)
                            }
                        }

                        //we call the first assassin frag
                        attackNextPlayer()

                    }
                }),
            Lord(FishType.AMBASSADOR,"L'Ermite",false, R.drawable.ermite,10,5,null,5,mockedPassivePermanentPower),
            Lord(FishType.SEA_SHELL,"L'Esclavagiste",true, R.drawable.esclavagiste,8,1,FishType.SEA_SHELL,5,
                object :ActivePermanentPower{

                    //must override this although it is initialized in the init() of the activepermanent power
                    override var isAvailable:Boolean=true

                    //Define what to do when you click on this Lord
                    override fun activate(player: Player, game: Game) {
                        //l'esclavagiste peut défausser 1 allie pour gagner 2 perles
                        //nous activons donc son pouvoir seulement s'il a au moins 1 allie
                        if(!player.listAlly.isEmpty())
                        {
                            //we clear the screen in order to have a full screen frag
                            MainActivity.generatorFragment!!.popAll()
                            controller!!.view.createPowerLordFrag(
                                player.listAlly,
                                "${player.nom} is using Esclavagiste\nPlease choose one ally to discard and gain 2 perls",
                                R.drawable.esclavagiste,
                                ::createViewHolderAllyCheckBox,
                            {ally->
                                //now we define that when he chooses an ally, we destroy it and he gains 2 perls
                                player.listAlly.remove(ally)
                                player.perl+=2
                                MainActivity.generatorFragment!!.popAll()
                            })
                        }
                    }

                }),
            Lord(FishType.SEA_HORSE,"L'Exploitant",false, R.drawable.exploitant,10,1,FishType.SEA_HORSE,12, noPower),
            Lord(FishType.JELLYFISH,"L'Illusionniste",false, R.drawable.illusionniste,10,1,FishType.JELLYFISH,9,mockedActivePermanentPower),

            Lord(FishType.JELLYFISH,"L'Invocateur",false, R.drawable.invocateur,8,1,FishType.JELLYFISH,8,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //we just hide the button that let go to the next player
                        controller!!.view.newTurnBegan()
                    }
                }),

            Lord(FishType.OCTOPUS,"L'Intriguant",false, R.drawable.l_intriguant,8,3,FishType.OCTOPUS,6,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {

                        //if the player has at least one lord to discard, and we can't discard the intriguant himself
                        val listFreeLordPlayer=player.listLord.filter { it.isFree && it.name!="L'Intriguant"}
                        if(!listFreeLordPlayer.isEmpty())
                        {
                            //we clear the screen in order to have a full screen frag
                            MainActivity.generatorFragment!!.popAll()

                            //we create a frag to show all the free lord of the player
                            controller!!.view.createPowerLordFrag(
                                listFreeLordPlayer,
                                "${player.nom} is using L' Intriguant\nDiscard One Lord and draw the first of the deck",
                                R.drawable.l_intriguant,
                                ::createViewHolderLord,
                            {lord->
                                //the player discard the chosen Lord in his hands
                                player.removeLord(lord)

                                //and we give him the first lord from the deck
                                val lordDrawn=game.court.drawOneLord()
                                //we give the lord to the player
                                //this can be null if there are no more Lords to be drawn
                                lordDrawn?.let{player.addLord(it)}

                                MainActivity.generatorFragment!!.popLast()

                                //we activate the power
                                lordDrawn?.power?.init(player, controller!!.game)
                            })
                        }
                    }
                }),

            Lord(FishType.SEA_HORSE,"La Bergère",true, R.drawable.la_bergere,8,1,FishType.SEA_HORSE,6, noPower),
            Lord(FishType.JELLYFISH,"La Chamanesse",true, R.drawable.la_chamanesse,6,3,FishType.JELLYFISH,5,
                object: MilitaryLordAttack{
                    //we just return false, like that the power can't be done
                    override fun isAttackAvailable(): Boolean {
                        return false
                    }
                }),
            Lord(FishType.SEA_HORSE,"La Gardienne",true, R.drawable.la_gardienne,6,3,FishType.SEA_HORSE,6, noPower),
            Lord(FishType.SEA_SHELL,"La Negociante",false, R.drawable.la_negociante,10,3,FishType.SEA_SHELL,9,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //we simply give 3 perls to the player
                        player.perl+=3
                    }
                }),
            Lord(FishType.SEA_SHELL,"Le Boutiquier",false, R.drawable.le_boutiquier,6,3,FishType.SEA_SHELL,9,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {
                        player.perl+=1
                    }
                }),

            Lord(FishType.CRAB,"Le Chasseur",false, R.drawable.le_chasseur,8,2,FishType.CRAB,6,
                object :InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //firslty we build a list with all the player that can be reached by the military power (i.e. chamanesse)
                        val listPlayerThatCanBeAttacked= mutableListOf<Player>()

                        //get all the other players
                        val listPlayerTargeted= mutableListOf<Player>().apply{addAll(game.listPlayer.listElt)}.filter{it.nom!=player.nom}

                        //TODO Becareful here , because we call the function under attack also the player is not already attacked
                        listPlayerTargeted.forEach {
                            it.playerUnderAttackMilitaryLord({player,_->
                                //if the player has at least one token
                                if(!player.listMonsterToken.isEmpty())
                                {
                                    listPlayerThatCanBeAttacked.add(player)
                                }
                            })
                        }

                        //after that we show to the current player wich player can be attacked
                        //and he has to choose one of them to steal the token from him
                        if(!listPlayerThatCanBeAttacked.isEmpty())
                        {
                            //we clear the screen in order to have a full screen frag
                            MainActivity.generatorFragment!!.popAll()
                            controller!!.view.createPowerLordFrag(
                                listPlayerThatCanBeAttacked,
                                "${player.nom} is using Hunter\nSteal the monster of one of the players",
                                R.drawable.le_chasseur,
                                ::createViewHolderPlayer,
                            {playerAttacked->
                                //we stole the token and give it to the other player
                                val stoleToken=playerAttacked.listMonsterToken.removeAt(0)
                                player.listMonsterToken.add(stoleToken)

                                //we clear the screen to delete the frag
                                MainActivity.generatorFragment!!.popAll()
                            })
                        }
                    }
                }),
            Lord(FishType.CRAB,"le chef des armées",true, R.drawable.le_chef_des_armees,8,1,FishType.CRAB,4,
                object: CountCardHand{

                    //just need to call that, the init function initiate the field with thr right name
                    override lateinit var nameOfAttackingPlayer: String

                    override fun activateOnOther(iterListTarget: Iterator<Player>, playerAttacking: Player) {

                        //what to do when you finish an assassin frag
                        fun attackNextPlayer() {
                            //tant qu'il y a des personnages
                            if (iterListTarget.hasNext()) {
                                val playerAttacked = iterListTarget.next()

                                //we create a frag to doscard ally and at the end we launch a second frag for another player
                                createFragToDiscardAlly(playerAttacked) {attackNextPlayer()}
                            }
                        }

                        //we call the first chief frag
                        attackNextPlayer()
                    }

                    //the second power of the chief of armies
                    //each turn you have to discard allies
                    override fun manageHandCards(player: Player) {
                        createFragToDiscardAlly(player)
                    }

                    private fun createFragToDiscardAlly(playerAttacked:Player,actionEndFrag:()->Unit={})
                    {
                        playerAttacked.playerUnderAttackMilitaryLord ({ _, _ ->

                            //on créé le frag pour discard les alliés
                            //on verifie que le joueur a plsu de 6 alliés sinon le pouvoir est inefficace
                            if (playerAttacked.listAlly.size>6)
                            {
                                controller!!.view.createPowerLordFrag(
                                    playerAttacked.listAlly,
                                    "$nameOfAttackingPlayer is using Chef des armees\n${playerAttacked.nom} click to delete Ally",
                                    R.drawable.le_chef_des_armees,
                                    ::createViewHolderAllyCheckBox,
                                    actionOnClick = { _,indexClicked ->
                                        //on delete l'allié
                                        //(listItem as MutableList<Ally>).removeAt(indexClicked)
                                        playerAttacked.listAlly.removeAt(indexClicked)

                                        //si il passe en dessous de la barre des 7 alliés en main on arrete
                                        if (playerAttacked.listAlly.size<=6) {
                                            MainActivity.generatorFragment!!.popLast()

                                            //If you want to do something special before ending the frag
                                            actionEndFrag()
                                        }
                                    }
                                )
                            }

                        },actionEndFrag)
                    }
                }),
            Lord(FishType.SEA_SHELL,"Le Colporteur",false, R.drawable.le_colporteur,8,1,FishType.SEA_SHELL,9,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //we simply give 3 perls to the player
                        player.perl+=2
                    }
                }),
            Lord(FishType.OCTOPUS,"Le Corrupteur",false, R.drawable.le_corrupteur,10,1,FishType.OCTOPUS,6,
                object:InstantPower{
                    override fun activate(player: Player, game: Game) {
                        //if the player has enough perl to use the power
                        if(player.perl>=5)
                        {
                            //so there may be conflicts with other frags
                            controller!!.view.createPowerLordFrag(
                                game.court.listProposedLord,
                                "${player.nom} is using Corrupteur\nPay 5 Perls for a Lord",
                                R.drawable.le_corrupteur,
                                ::createViewHolderLord,
                                { lordBought->
                                    //we take the perl necessary for buying
                                    player.perl-=5
                                    //we add the lord to the player without pay
                                    player.addLord(lordBought)

                                    game.court.getLordAndRemoveItFromProposed(lordBought)

                                    //and we check if something happen to the court
                                    game.court.lordIsActuallyBought(player)

                                    MainActivity.generatorFragment!!.popLast()

                                    lordBought.power.init(player, controller!!.game)
                                }
                            )
                        }

                    }
                }),
            Lord(FishType.OCTOPUS,"Le Diplomate",true, R.drawable.le_diplomate,8,1,FishType.OCTOPUS,5,
                object : BuyLordColorAllie{
                    override fun isAuthorizedToBuy(listDifferentTypeUseForBuy: List<FishType>, lordToBuy: Lord): Boolean
                    {
                        //Le Diplomate doesn't need to check if the player possess the right type, so we return
                        //always true
                        return true
                    }
                }),

            Lord(FishType.SEA_HORSE,"Le Faucheur",true, R.drawable.le_faucheur,7,2,FishType.SEA_HORSE,6, noPower),

            Lord(FishType.CRAB,"Le Geôlier",false, R.drawable.le_geolier,6,3,FishType.CRAB,7,
                object: InfluenceAllOthers{
                    override fun activateOnOther(iterListTarget: Iterator<Player>, playerAttacking: Player) {
                        fun attackNextPlayer() {
                            //tant qu'il y a des personnages
                            if (iterListTarget.hasNext()) {
                                val playerAttacked = iterListTarget.next()

                                //we create a frag to doscard ally and at the end we launch a second frag for another player
                                createFragToDiscardAlly(playerAttacking.nom,playerAttacked,::attackNextPlayer)
                            }
                        }

                        //we call the first chief frag
                        attackNextPlayer()
                    }

                    private fun createFragToDiscardAlly(nameOfAttackingPlayer:String,playerAttacked:Player,actionEndFrag:()->Unit)
                    {
                        playerAttacked.playerUnderAttackMilitaryLord ({ _, _ ->

                            //on créé le frag pour discard les alliés
                            //on verifie que le joueur a 1 allié sinon le pouvoir est inefficace
                            if (playerAttacked.listAlly.size>0)
                            {
                                controller!!.view.createPowerLordFrag(
                                    playerAttacked.listAlly,
                                    "$nameOfAttackingPlayer is using Le geôlier\n${playerAttacked.nom} click to delete 1 Ally",
                                    R.drawable.le_geolier,
                                    ::createViewHolderAllyCheckBox,
                                    { allyToDelete ->

                                        playerAttacked.listAlly.remove(allyToDelete)


                                        MainActivity.generatorFragment!!.popLast()

                                        //If you want to do something special before ending the frag
                                        actionEndFrag()

                                    }
                                )
                            }

                        },actionEndFrag)
                    }
                }),
            Lord(FishType.JELLYFISH,"Le Maître de magie",true, R.drawable.le_maitre_de_magie,10,3,FishType.JELLYFISH,6,
                object : BoughtLordFederateAllie{
                    override fun federateAllie(player: Player, listAllyUsedToBuy: List<Ally>) {
                        //If the player has actually at least one ally to federate
                        if(!listAllyUsedToBuy.isEmpty())
                        {
                            controller!!.view.createPowerLordFrag(
                                listAllyUsedToBuy,
                                "${player.nom} is Using Le maitre de magie\nPlease choose an ally to federate",
                                R.drawable.le_maitre_de_magie,
                                ::createViewHolderAllyCheckBox,
                                {ally->
                                    //we federate this ally to the player
                                    player.addFederatedAlly(ally)
                                    MainActivity.generatorFragment!!.popLast()
                                })
                        }
                    }
                }),
            Lord(FishType.SEA_HORSE,"Le Meunier",false, R.drawable.le_meunier,8,2,FishType.SEA_HORSE,10, noPower),
            Lord(FishType.CRAB,"Le Questeur",false, R.drawable.le_questeur,7,2,FishType.CRAB,7,
                object :InfluenceAllOthers{
                    override fun activateOnOther(iterListTarget: Iterator<Player>, playerAttacking: Player) {
                        iterListTarget.forEach {
                            //we check if the player has at least 2 perls otherwise we don't want him to have
                            //a negative number of pearls
                            if(it.perl>=2)
                            {
                                it.perl-=2
                            }
                            else
                            {
                                it.perl=0
                            }
                        }
                    }
                }
                ),
            Lord(FishType.CRAB,"Le Recruteur",true, R.drawable.le_recruteur,10,2,FishType.CRAB,4,
                //the interface order is very important because we use for the name of the first interface
                //for the key in the hashmap rulesPower
                object : BuyLordPrice,PassivePowerInfluenceOthers{
                    //just need to call that, the init function initiate the field with thr right name
                    override lateinit var nameOfAttackingPlayer: String

                    //we need to override this to precise
                    //that we init this as a military power
                    override fun init(player: Player, game: Game) {
                        super<PassivePowerInfluenceOthers>.init(player, game)
                    }

                    override fun computePrice(originPrice: Int): Int {
                        return originPrice+2
                    }
                }),
            Lord(FishType.SEA_SHELL,"Le Rentier",true, R.drawable.le_rentier,10,2,FishType.SEA_SHELL,5,
                object:EndTurnPower{
                    override fun bonusToThePlayer(player: Player) {
                        player.perl+=1
                    }
                }),
            Lord(FishType.AMBASSADOR,"Le Sage",false, R.drawable.le_sage,10,5,null,4,mockedActivePermanentPower),
            Lord(FishType.OCTOPUS,"Le Traitre",false, R.drawable.le_traitre,12,3,FishType.OCTOPUS,6,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {

                        //if the player has at least one lord to discard, and we can't discard the traitre himself
                        val listFreeLordPlayer=player.listLord.filter { it.isFree && it.name!="Le Traitre"}
                        if(!listFreeLordPlayer.isEmpty())
                        {
                            //we clear the screen in order to have a full screen frag
                            MainActivity.generatorFragment!!.popAll()

                            //we create a frag to show all the free lord of the player
                            controller!!.view.createPowerLordFrag(
                                listFreeLordPlayer,
                                "${player.nom} is using Le Traitre\nDiscard One Lord",
                                R.drawable.le_traitre,
                                ::createViewHolderLord,
                                {lord->
                                    //the player discard the chosen Lord in his hands
                                    player.removeLord(lord)

                                    MainActivity.generatorFragment!!.popLast()

                                    //and then we create a court frag to take one of the lord
                                    controller!!.view.createPowerLordFrag(
                                        game.court.listProposedLord,
                                        "${player.nom} is using Le Traitre\nChoose one new Lord",
                                        R.drawable.le_traitre,
                                        ::createViewHolderLord,
                                    { lordChosen->

                                        //we add the lord to the player without pay
                                        player.addLord(lordChosen)

                                        //and we check if something happen to the court
                                        game.court.getLordAndRemoveItFromProposed(lordChosen)

                                        //don't forget to check if the court si almost empty
                                        game.court.lordIsActuallyBought(player)

                                        MainActivity.generatorFragment!!.popLast()

                                        lordChosen.power.init(player, controller!!.game)
                                    })
                                }
                            )
                        }
                    }
                }),
            Lord(FishType.OCTOPUS,"Le Trésorier",true, R.drawable.le_tresorier,10,2,FishType.OCTOPUS,5,
                object: BuyLordPrice{
                    override fun computePrice(originPrice: Int): Int {
                        return originPrice-2
                    }
                }),
            Lord(FishType.OCTOPUS,"L' Opportuniste",true, R.drawable.opportuniste,6,3,FishType.OCTOPUS,5,
                object : InstantPower{
                    override fun activate(player: Player, game: Game) {

                        //we clear the screen in order to have a full screen frag
                        MainActivity.generatorFragment!!.popAll()

                        //and then we create a court frag to take one of the lord
                        controller!!.view.createPowerLordFrag(
                            game.court.listProposedLord,
                            "${player.nom} is using L'Opportuniste\nDiscard One Lord From Court",
                            R.drawable.opportuniste,
                            ::createViewHolderLord,{ lordChosen->

                            game.court.getLordAndRemoveItFromProposed(lordChosen)

                            //add New Lord to the court
                            game.court.drawAndAddLordToCourt()

                            MainActivity.generatorFragment!!.popLast()

                        })
                    }
                }),
            Lord(FishType.JELLYFISH,"L'Oracle",true, R.drawable.oracle,8,2,FishType.JELLYFISH,5,
                object : ActivePermanentPower
                {
                    override var isAvailable: Boolean=true

                    override fun activate(player: Player, game: Game) {
                        controller!!.view.createPowerLordFrag(
                            game.council.getAllCurrentAvailableStack(),
                            "${player.nom} is using L'Oracle\nDiscard one council stack",
                            R.drawable.oracle,
                            ::createViewHolderImageOnly,{ fishType->
                                game.council.removeStack(fishType)
                                MainActivity.generatorFragment!!.popLast()
                            })
                    }
                })
        )
    }
}