package fr.emile.abyss.modelClass.gameItems

import fr.emile.abyss.affichage.IShowImage

class Ally (val number:Int=0, val type:FishType):IShowImage{
    override var imgId: Int=type.imgId

    //gui to click on image , mean want to use to buy lord
    var selectedToBuyLord:Boolean=false

    override fun toString(): String {
        return "Ally\n(number=$number,\n type=$type)"
    }
}