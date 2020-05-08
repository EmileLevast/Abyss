package fr.emile.abyss.modelClass

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
}