package fr.emile.abyss.modelClass.gameItems

import kotlin.math.sqrt

//the number of different allies (jellyfish, crab...)
const val NUMBER_OF_ALLIE_TYPE=5

//the number maximum that an allie can have
const val NUMBER_MAX_ALLIE=5

class Deck {
    var stackAllie:MutableList<Allie>

    init {
        //we get an instance of all the type of fish
        val listFishType=fishType.values()
        val numberAllieForType=
            partialSum(NUMBER_MAX_ALLIE)
        val numberTotalOfAllie= NUMBER_OF_ALLIE_TYPE * numberAllieForType

        //we populate the deck
        //we populate like this : "Jelly Fish 111112222333445 ...
        stackAllie= MutableList(numberTotalOfAllie){ i->Allie(((6.0- invPartialSum(
            i % numberAllieForType + 1
        )).toInt()),
            listFishType[i/numberAllieForType])}

        //we shuffle the deck
        stackAllie.shuffle()
    }

    companion object {

        //1+2+3+4+5+6+...=
        fun partialSum(n:Int)=n*(n+1)/2

        //renvoi l'antécédent de la somme partielle
        fun invPartialSum(x:Int):Double
        {
            return(-1.0+ sqrt(1.0+8.0*(x.toFloat())))/2.0
        }
    }

    override fun toString(): String {
        return "Deck :\n" + stackAllie.joinToString(separator = "\n") { it.toString() }
    }


}