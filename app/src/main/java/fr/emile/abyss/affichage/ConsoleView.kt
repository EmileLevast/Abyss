package fr.emile.abyss.affichage

import android.util.Log
import fr.emile.abyss.MainActivity
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class ConsoleView : IView{
    override fun showGame(game: Game) {
        Log.w("msg",game.toString())
    }

    override fun updateExploration(exploration: Exploration) {
        Log.w("msg",exploration.toString())
    }

    override fun createExploration(exploration: Exploration) {
        //rien a initialiser pour un affichage console
    }
}