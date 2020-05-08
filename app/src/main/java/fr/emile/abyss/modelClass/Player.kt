package fr.emile.abyss.modelClass

import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.modelClass.gameItems.*

private const val NBR_MAX_LORD=7

class Player (var nom:String, override var imgId:Int):IShowImage{

    //true quand le joueur a deja achete un allie dans cette explo
    var dejaAcheteExplo=false

    //contains all the monster tokens that the player won and didn't use yet
    var listMonsterToken= mutableListOf<MonsterToken>()
    //count the number of key that you get with monster token
    var nbrKeyToken:Int=0
    var listLocation= mutableListOf<Location>()

    var listAllieFedere= mutableListOf<Ally>()
    var listLord= mutableListOf<Lord>()

    var perl=1
    var listAlly=mutableListOf<Ally>()

    var listRulesPower:RulesPower= RulesPower()

    fun buyAllieCard(cardToAdd:Ally, cost:Int)
    {
        listAlly.add(cardToAdd)
        perl-=cost

        dejaAcheteExplo=true
    }

    /**Take the [lordToBuy] and add it to the purchased Lords [listLord] , and federate the allie**/
    fun buyLord(lordToBuy:Lord, listAllyUsedToBuy:List<Ally>)
    {
        addLord(lordToBuy)
        /**!!Surtout ne pas supprimer les allies qui ont servi a l'achat car il faut encore les envoyer à la defausse
         * C'est fait dans [Game::sendPlayerToDiscard]!!
         * Par contre il faut quand même supprimé l'allié qui a ete federe mais c'est fait dans [addFederatedAlly]**/

        //federate allies
        //we search the rule for federating ally
        listRulesPower.applyToCorrespondingEvent<BoughtLordFederateAllie>(object : BoughtLordFederateAllie{},this)
        {
            //and we apply each power for federation, becareful if there are many powers , the ally may be added several time
            //as it is done in the power for now
            it.federateAllie(this,listAllyUsedToBuy)
        }
    }

    fun addFederatedAlly(newFederatedAlly:Ally)
    {
        //on ajoute l'allie a la liste d'allie federe
        listAllieFedere.add(newFederatedAlly)

        //et on le supprime des alliés en mains (car ensuite le reste est envoyé dans la défausse dans [game]
        listAlly.remove(newFederatedAlly)
    }

    /**Use this to add a lord the hand of player**/
    fun addLord(lordToBuy:Lord)
    {
        listLord.add(lordToBuy)
    }

    fun addAllie(cardToAdd:Ally)
    {
        listAlly.add(cardToAdd)
    }

    fun addAllie(listCardToAdd:MutableList<Ally>)
    {
        listAlly.addAll(listCardToAdd)
    }

    /**use this to remove a lord, because if you do'nt the power (passivePermamnentPower) may be still enabled for the player**/
    fun removeLord(lordToRemove:Lord)
    {
        //we delete his passive power
        lordToRemove.removePower(this)

        //we delete the lord
        listLord.remove(lordToRemove)
    }

    /**Implement this with the assassin**/
    fun lordIsKilled(killedLord:Lord)
    {
        killedLord.die(this)
    }

    fun hasMaxNbrLord():Boolean
    {
        return listLord.size>= NBR_MAX_LORD
    }

    /**Location**/
    fun watchForBuyLocation()
    {
        //first we retrieve all the available keys from the lords
        val listLordAvailableKey=listLord.filter { it.hasKey && it.isFree && it.isAlive }

        //we check if the player has enough key
        if(nbrKeyToken+listLordAvailableKey.size >= 3)
        {
            canBuyANewLocation(listLordAvailableKey)
        }
    }

    private fun canBuyANewLocation(listLordAvailableKey:List<Lord>)
    {
        var keyNeeded=3
        //If I have less than 3 key token
        if(keyNeeded>nbrKeyToken)
        {
            //I can min the number of key needed
            keyNeeded-=nbrKeyToken
            //and I have no more key token
            nbrKeyToken=0

            //Because we still need key for buying lord
            //we get the sufficient number of lord for buy a location
            val listLordUseForBuyLocation=listLordAvailableKey.take(keyNeeded)
            //and then we make them "unFree" and remove their power
            listLordUseForBuyLocation.forEach { it.useToBuyLocation(this) }


        }else
        {
            nbrKeyToken-=keyNeeded
        }

        //TODO now we can launch a frag for buying the location
    }

    fun buyLocation(locationToBuy:Location)
    {
        listLocation.add(locationToBuy)
    }


    /**call this function to execute a non-passive miltary power**/
    fun playerUnderAttackMilitaryLord(powerEffect:(playerAttacking:Player,Game)->Unit,actionOnUnvalidAttack:()->Unit={})
    {
        listRulesPower.applyPowerReactingToMilitaryLord(powerEffect,this)
    }
}