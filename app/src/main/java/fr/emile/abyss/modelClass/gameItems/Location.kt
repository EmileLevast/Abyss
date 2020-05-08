package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.modelClass.Player

/**
 * [effectLocation] is the function that will called to determine the number of Influence Point
 * The function return the Influence Point that gives this location
 * **/
class Location(override var imgId: Int,var name:String, val effectLocation:(playerOwner: Player)->Int):IShowImage
{

    companion object {
        val listLocation= listOf<Location>(
            Location(R.drawable.la_caserne,"La Caserne") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*7 +2)}
        )
    }
}