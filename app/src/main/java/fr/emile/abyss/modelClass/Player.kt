package fr.emile.abyss.modelClass

import fr.emile.abyss.modelClass.gameItems.Allie
import fr.emile.abyss.modelClass.gameItems.Lord

class Player (var nom:String){

    //true quand le joueur a deja achete un allie dans cette explo
    var dejaAcheteExplo=false

    //quand le joueur selectionnne des cartes parmi sa main pour acheter
    //elles sont enlevees de sa main et ajoutées à cette liste
    var listCardToBuy= mutableListOf<Allie>()
    var listAllieFedere= mutableListOf<Allie>()
    var listLord= mutableListOf<Lord>()

    var perl=1
    var listAllie=mutableListOf<Allie>()

    fun buyAllieCard(cardToAdd:Allie,cost:Int)
    {
        listAllie.add(cardToAdd)
        perl-=cost

        dejaAcheteExplo=true
    }

    fun getAllie(cardToAdd:Allie)
    {
        listAllie.add(cardToAdd)
    }



}