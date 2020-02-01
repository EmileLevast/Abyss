package fr.emile.abyss.affichage

import fr.emile.abyss.MainActivity
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

interface IView {

    fun showGame(game: Game)

    fun updateExploration(exploration: Exploration)

    fun createExploration(exploration: Exploration)

}