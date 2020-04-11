package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage

enum class FishType(override var imgId:Int):IShowImage {
    CRAB(R.drawable.crab_ally),
    JELLYFISH(R.drawable.jellyfish_ally),
    OCTOPUS(R.drawable.octopussy_ally),
    SEA_HORSE(R.drawable.sea_horse_ally),
    SEA_SHELL(R.drawable.sea_shell_ally),
    AMBASSADOR(R.drawable.ancien);


    companion object {
        fun getListAlly(): MutableList<FishType> {
            //on enleve l'amabssadeur qui ne compte pas dans les allies achetable
            val listAlly = values().toMutableList()
            listAlly.remove(AMBASSADOR)
            return listAlly
        }
    }
}

