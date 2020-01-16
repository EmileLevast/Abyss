package fr.emile.abyss.Affichage

import android.util.Log
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

interface IView {

    fun showGame(game: Game)


    fun showExploration(exploration: Exploration)

}