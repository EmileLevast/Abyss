package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.modelClass.Player
import java.util.*

/**
 * [effectLocation] is the function that will called to determine the number of Influence Point
 * The function return the Influence Point that gives this location
 * **/
class Location(override var imgId: Int,var name:String, val effectLocation:(playerOwner: Player)->Int):IShowImage
{

    companion object {
        val listLocation= listOf(
            Location(R.drawable.la_caserne,"La Caserne") { player->(player.listLord.filter { it.FishType==FishType.CRAB}.size*2 +7)},

            Location(R.drawable.assemblee_oceanique_du_senat,"L' Assemblée Océanique du Sénat") { player->(player.listAllieFedere.filter { it.type==FishType.OCTOPUS}.size*3 +4)},

            Location(R.drawable.la_barriere_de_corail,"La barriere de corail") { player->20-player.listAllieFedere.size},

            Location(R.drawable.la_chambre_des_allies,"La Chambre des alliés") { player->(player.listAllieFedere.groupBy ({allie->allie.type},{allie->allie.number}).values.map{ Collections.min(it)}.sum())},

            Location(R.drawable.la_fosse_au_tridacnas_geants,"La Fosse aux tridacnas géants") { player->(player.listAllieFedere.filter { it.type==FishType.SEA_SHELL}.size*3 +3)},

            Location(R.drawable.la_salle_du_trone,"La Salle du trone") { player->(player.listLord.maxBy {it.influencePoint}!!.influencePoint)},

            Location(R.drawable.la_tour_close,"La Tour Close") { player->(3*player.listLord.filter {it.hasKey}.size)},

            Location(R.drawable.la_tour_perdue,"La Tour Perdue") { player->(3*player.listLord.filter {!it.hasKey}.size)},

            Location(R.drawable.le_gouffre,"Le Gouffre") { player->(player.listAllieFedere.filter { it.type==FishType.CRAB}.size*3 +5)},

            Location(R.drawable.le_sanctuaire,"Le Sanctuaire") { player->(player.listAllieFedere.filter { it.type==FishType.JELLYFISH}.size*3 +4)},

            Location(R.drawable.le_parlement,"Le Parlement") { player->(player.listLord.filter { it.FishType==FishType.OCTOPUS}.size*2 +6)},

            Location(R.drawable.les_abysses,"Les Abysses") { player->(player.listLord.distinctBy { it.FishType}.size*2)},

            Location(R.drawable.les_bas_fond,"Les Bas-fond") { player->(player.listLord.minBy {it.influencePoint}!!.influencePoint*2)},

            Location(R.drawable.les_champs_de_sargasse,"Les Champs de Sargasse") { player->(player.listAllieFedere.filter { it.type==FishType.SEA_HORSE}.size*3 +4)},

            Location(R.drawable.les_geoles,"Les Geoles") { player->(15-player.listLord.size)},

            Location(R.drawable.les_quais_de_chargement,"Les Quais de Chargement") { player->(player.listLord.filter { it.FishType==FishType.SEA_SHELL}.size*2 +5)},

            Location(R.drawable.les_reserves_d_hydrozoas,"Les reserves d'hydrozoas") { player->(player.listLord.filter { it.FishType==FishType.JELLYFISH}.size*2 +6)},

            Location(R.drawable.les_silos_sargasse,"Les Silos à sargasse") { player->(player.listLord.filter { it.FishType==FishType.SEA_HORSE}.size*2 +5)}

            )
    }
}