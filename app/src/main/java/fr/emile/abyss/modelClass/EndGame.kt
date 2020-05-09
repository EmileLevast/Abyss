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
        return listInfluencePlayer.maxBy { it.influencePointTotal }!!
    }

}

class InfluencePlayer(val player: Player)
{
    var influencePointTotal:Int=0

    var influencePointLord:Int=0
    var influencePointLocation:Int=0
    var influencePointAllie:Int=0

    init {
        calculatePoint()
    }

    /**
     * compute the number of points that the player has and put it inside [influencePoint]
     */
    fun calculatePoint()
    {
        //avant tout on federe les alliés dans la main
        player.listAllieFedere.addAll(player.listAlly.groupBy ({allie->allie.type},{allie->allie}).
            values.map{ Collections.min(it) { ally1, ally2->ally1.number.compareTo(ally2.number)} })

        //on parcours les seigneurs et selectionne leur nbr de point d'influence pour les sommer
        influencePointLord=player.listLord.sumBy { lord->lord.influencePoint }

        //alors ici, c'est fun
        //on prend notre liste d'allies federe
        //on regroupe en une map avec comme cle le type de l'allie et en valeur une list de nombre de pt d'influence
        //on prend cette map on la transforme en une list dont chaque element est la valeur maximale de chaque fishtype
        //et après on somme tous ces points d'influence
        influencePointAllie=player.listAllieFedere.groupBy ({allie->allie.type},{allie->allie.number}).
            values.map { Collections.max(it) }.sum()

        influencePointLocation=player.listLocation.sumBy { it.effectLocation(player) }

        //on calcul le total
        influencePointTotal+=influencePointAllie+influencePointLord+influencePointLocation
    }

    override fun toString(): String {
        return "InfluencePlayer(player=${player.nom}, influencePoint=$influencePointTotal)"
    }
}

