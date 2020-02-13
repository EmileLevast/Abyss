package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.Button
import android.widget.TextView
import fr.emile.abyss.R
import fr.emile.abyss.controller
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.modelClass.Exploration

class ExplorationFrag(private val exploration: Exploration):CustomFragment<Exploration>()
{
    override val idLayoutToInflate: Int= R.layout.frag_layout_exploration


    lateinit var textViewCardToBuy:TextView
    lateinit var textCardRefused:TextView
    lateinit var textCostExplo:TextView

    lateinit var buyButton:Button
    lateinit var notBuyButton:Button


    override fun updateView(dataGame: Exploration) {
        textViewCardToBuy.text=dataGame.listProposedCard.last().toString()
        textCostExplo.text=dataGame.currentCost.toString()
        textCardRefused.text=dataGame.listProposedCard.size.toString()

    }

    override fun createView(viewInflated: View) {
        textViewCardToBuy=viewInflated.findViewById(R.id.TextcardToBuy)
        textCardRefused=viewInflated.findViewById(R.id.TextcardRefused)
        textCostExplo=viewInflated.findViewById(R.id.textCostExplo)

        buyButton=viewInflated.findViewById(R.id.buttonBuy)
        notBuyButton=viewInflated.findViewById(R.id.buttonNotBuy)

        buyButton.setOnClickListener { controller?.playerBuyOrNotExploCard(Exploration.Choice.BUY) }
        notBuyButton.setOnClickListener { controller?.playerBuyOrNotExploCard(Exploration.Choice.PASS) }

        //we make the first update
        updateView(exploration)
    }

}
