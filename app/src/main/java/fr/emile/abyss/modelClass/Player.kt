package fr.emile.abyss.modelClass

import fr.emile.abyss.modelClass.gameItems.Allie

class Player (var nom:String){

    //true quand le joueur a deja achete un allie dans cette explo
    var dejaAcheteExplo=false

    var perl=1
    private var listAllie=mutableListOf<Allie>()

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