package fr.emile.abyss.modelClass

import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage

class ConfigGame {
    //the list of the player currently waiting to be add to the game
    var listPlayersToAddToGame= mutableListOf<Player>()

    fun addAPlayer(name:String,idImage:Int)
    {
        //if there is no other player with same name
        if(name.isNotBlank() && !listPlayersToAddToGame.any { it.nom == name })
        {
            listPlayersToAddToGame.add(Player(name,idImage))
        }
    }

    fun removePlayer(nameOfPlayerToBeRemoved:String)
    {
        listPlayersToAddToGame.removeIf { it.nom==nameOfPlayerToBeRemoved }
    }

    fun reset() {
        listPlayersToAddToGame.clear()
    }

    companion object {
        val listImagePlayer= mutableListOf<IShowImage>(
            object :IShowImage{
                override var imgId: Int=R.drawable.couverture
            },
            object :IShowImage{
                override var imgId: Int=R.drawable.conspiracy_meduse
            },
            object :IShowImage{
                override var imgId: Int=R.drawable.couverture_ancien
            },
            object :IShowImage{
                override var imgId: Int=R.drawable.couverture_agriculteur
            },
            object :IShowImage{
                override var imgId: Int=R.drawable.couverture_militaire
            }
        )
    }
}