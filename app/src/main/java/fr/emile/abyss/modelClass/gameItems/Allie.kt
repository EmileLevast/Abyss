package fr.emile.abyss.modelClass.gameItems

class Allie (val number:Int=0,val type:FishType){

    //gui to click on image , mean want to use to buy lord
    var selectedToBuyLord:Boolean=false

    override fun toString(): String {
        return "Allie\n(number=$number,\n type=$type)"
    }
}