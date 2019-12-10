package fr.emile.abyss

import android.util.Log
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class View {

    fun showGame(game: Game)
    {
        Log.w("msg",game.toString())
    }

    fun showExploration(exploration: Exploration)
    {
        Log.w("msg",exploration.toString())
    }
}