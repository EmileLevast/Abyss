package fr.emile.abyss.Affichage

import android.util.Log
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class ConsoleView : IView{
    override fun showGame(game: Game) {
        Log.w("msg",game.toString())
    }

    override fun showExploration(exploration: Exploration) {
        Log.w("msg",exploration.toString())
    }
}