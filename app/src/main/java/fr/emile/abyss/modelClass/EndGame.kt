package fr.emile.abyss.modelClass

import fr.emile.abyss.Container
import java.util.Collections

class EndGame(var allPlayer:Container<Player>) {

    //initialized in [computeAllPLayerInfluencePoint()]
    private lateinit var listInfluencePlayer:List<InfluencePlayer>

    init {
        //construct a list with all player and their Influence Point
        computeAllPlayerInfluencePoint()
    }

    fun computeAllPlayerInfluencePoint()
    {
        listInfluencePlayer=allPlayer.listElt.map { InfluencePlayer(it) }
    }

    //return the player with the more influence point
    fun getBestPlayer(): InfluencePlayer
    {
        return listInfluencePlayer.maxBy { it.influencePoint }!!
    }

    //return true if the current player has already bought max lord
    fun isGameFinished():Boolean
    {
        val res=allPlayer.getCurrent().hasMaxNbrLord()
        if(res)
        {
            computeAllPlayerInfluencePoint()
        }
        return res
    }
}

class InfluencePlayer(val player: Player)
{
    var influencePoint:Int=0


    init {
        calculatePoint()
    }

    /**
     * compute the number of points that the player has and put it inside [influencePoint]
     */
    fun calculatePoint()
    {
        //on parcours les seigneurs et selectionne leur nbr de point d'influence pour les sommer
        val influencePointLord=player.listLord.sumBy { lord->lord.influencePoint }

        //alors ici, c'est fun
        //on prend notre liste d'allies federe
        //on regroupe en une map avec comme cle le type de l'allie et en valeur une list de nombre de pt d'influence
        //on prend cette map on la transforme en une list dont chaque element est la valeur maximale de chaque fishtype
        //et après on somme tous ces points d'influence
        val influencePointAllie=player.listAllieFedere.groupBy ({allie->allie.type},{allie->allie.number}).
            values.map { Collections.max(it) }.sum()

        //on calcul le total
        influencePoint+=influencePointAllie+influencePointLord

    }

    override fun toString(): String {
        return "InfluencePlayer(player=${player.nom}, influencePoint=$influencePoint)"
    }
}

