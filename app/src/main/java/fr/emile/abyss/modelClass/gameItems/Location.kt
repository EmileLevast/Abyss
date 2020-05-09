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
        val listLocation= listOf(
            Location(R.drawable.la_caserne,"La Caserne") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*2 +7)},
            Location(R.drawable.les_bas_fond,"Les Bas-Fond") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*7 +2)},
            Location(R.drawable.les_champs_de_sargasse,"Les Champs de Sargasse") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*7 +2)},
            Location(R.drawable.les_abysses,"Les Abysses") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*7 +2)},
            Location(R.drawable.les_quais_de_chargement,"Les quais de chargement") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*7 +2)},
            Location(R.drawable.les_silos_sargasse,"Les Silos de Sargasse") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*7 +2)}
        )
    }
}