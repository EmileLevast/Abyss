package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.controller
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderAlly
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.gameItems.Ally

class ExplorationFrag(private val exploration: Exploration):CustomFragment<Exploration>()
{
    override val idLayoutToInflate: Int= R.layout.frag_layout_exploration


    lateinit var textCostExplo:TextView

    lateinit var buyButton:Button
    lateinit var notBuyButton:Button

    lateinit var recyclerViewAlly: HorizontalRecyclerView
    private lateinit var adapterAlly: ImageAdapter<Ally>


    override fun createView(viewInflated: View) {
        textCostExplo=viewInflated.findViewById(R.id.textCostExplo)

        buyButton=viewInflated.findViewById(R.id.buttonBuy)
        notBuyButton=viewInflated.findViewById(R.id.buttonNotBuy)

        buyButton.setOnClickListener { controller?.playerBuyOrNotExploCard(Exploration.Choice.BUY) }
        notBuyButton.setOnClickListener { controller?.playerBuyOrNotExploCard(Exploration.Choice.PASS) }

        recyclerViewAlly=viewInflated.findViewById(R.id.recyclerViewAllieExploration)

        recyclerViewAlly.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        adapterAlly= object : ImageAdapter<Ally>(exploration.listProposedCard,activity!!,0.4f,0.1f,recyclerViewAlly, ::createViewHolderAlly){
            override fun onClickItem(position: Int) {
                Log.w("msg", "pos:$position")
            }
        }

        recyclerViewAlly.adapter=adapterAlly

        //we make the first update
        updateView(exploration)
    }

    override fun updateView(dataGame: Exploration) {
        val priceText="Current Price: ${dataGame.currentCost}"
        textCostExplo.text=priceText

        adapterAlly.notifyDataSetChanged()

    }

}
