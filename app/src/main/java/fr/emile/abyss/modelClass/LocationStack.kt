package fr.emile.abyss.modelClass

import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.Location

class LocationStack {
    //contient tous les seigneurs restants de la pioche
    private var deckLocation= mutableListOf<Location>()

    //contient les seigneurs de la court
    var listAvailableLocation= mutableListOf<Location>()

    var listJustDrawnLocation= mutableListOf<Location>()
    init {
        initalize()
    }

    fun initalize()
    {
        deckLocation.clear()
        listAvailableLocation.clear()
        listJustDrawnLocation.clear()

        deckLocation.addAll(Location.listLocation)
        deckLocation.shuffle()

        listAvailableLocation.add(deckLocation.removeAt(0))
    }

    fun drawNewLocation(nbrDraw:Int)
    {
        //we add the drawn Location to the available location
        listJustDrawnLocation.addAll(deckLocation.take(nbrDraw))

        //we remove them from the deck
        deckLocation.drop(nbrDraw)

        if(deckLocation.isEmpty())
        {
            controller!!.gameFinished()
        }
    }

    fun locationBought(location: Location) {

        //we give the drawn location to the available location
        listAvailableLocation.addAll(listJustDrawnLocation)
        listJustDrawnLocation.clear()

        //and only then we remove in the result list of available location
        //the location bought by the player
        listAvailableLocation.remove(location)
    }
}