package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.TextView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.modelClass.EndGame

class EndGameFrag(var endGame: EndGame):CustomFragment<EndGame>() {
    override val idLayoutToInflate: Int
        get() = R.layout.frag_layout_end_game //To change initializer of created properties use File | Settings | File Templates.

    private lateinit var textViewWinnerName:TextView
    private lateinit var textViewAllyInfluence:TextView
    lateinit var textViewLordInfluence:TextView
    lateinit var textViewLocationInfluence:TextView

    override fun createView(viewInflated: View) {
        textViewWinnerName=viewInflated.findViewById(R.id.textViewEndGameNameWinner)
        textViewAllyInfluence=viewInflated.findViewById(R.id.textViewEndGameAllyPoint)
        textViewLocationInfluence=viewInflated.findViewById(R.id.textViewEndGameLocationPoint)
        textViewLordInfluence=viewInflated.findViewById(R.id.textViewEndGameLordPoint)
        updateView(endGame)
    }

    override fun updateView(dataGame: EndGame) {
        textViewWinnerName.text="${dataGame.getBestPlayer().player.nom} win with ${dataGame.getBestPlayer().influencePointTotal} IP"

        textViewAllyInfluence.text="Ally :${dataGame.getBestPlayer().influencePointAllie} IP"
        textViewLordInfluence.text="Lord :${dataGame.getBestPlayer().influencePointLord} IP"
        textViewLocationInfluence.text="Location :${dataGame.getBestPlayer().influencePointLocation} IP"
    }
}