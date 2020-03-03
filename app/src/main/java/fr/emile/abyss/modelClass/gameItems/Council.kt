package fr.emile.abyss.modelClass.gameItems

class Council {
    var decksAllie= hashMapOf<FishType,MutableList<Ally>>()

    init {
        decksAllie[FishType.CRAB] = mutableListOf()
        decksAllie[FishType.JELLYFISH] = mutableListOf()
        decksAllie[FishType.SEA_HORSE] = mutableListOf()
        decksAllie[FishType.SEA_SHELL] = mutableListOf()
        decksAllie[FishType.OCTOPUS] = mutableListOf()
    }

    fun addExplorationDroppedCards(listCards:MutableList<Ally>)
    {
        listCards.forEach { decksAllie[it.type]?.add(it) }
    }

    fun takeStack(fishTypeOfChosenStack: FishType):MutableList<Ally>
    {
        val cardsToRetrieve=decksAllie[fishTypeOfChosenStack]?.toMutableList()
        decksAllie[fishTypeOfChosenStack]?.clear()
        return cardsToRetrieve!!
    }
}