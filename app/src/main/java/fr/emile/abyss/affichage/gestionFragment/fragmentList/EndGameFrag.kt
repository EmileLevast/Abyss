package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.TextView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.modelClass.EndGame

class EndGameFrag(var endGame: EndGame):CustomFragment<EndGame>() {
    override val idLayoutToInflate: Int
        get() = R.layout.frag_layout_end_game //To change initializer of created properties use File | Settings | File Templates.

    lateinit var textViewVictory:TextView

    override fun createView(viewInflated: View) {
        textViewVictory=viewInflated.findViewById(R.id.textVictoryScreen)
        updateView(endGame)
    }

    override fun updateView(dataGame: EndGame) {
        textViewVictory.text="Victoire: \n"+dataGame.getBestPlayer().toString()
    }
}