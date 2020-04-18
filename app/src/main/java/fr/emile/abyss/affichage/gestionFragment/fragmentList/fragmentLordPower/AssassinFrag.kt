package fr.emile.abyss.affichage.gestionFragment.fragmentList.fragmentLordPower

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderLord
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.Lord

class AssassinFrag(val playerKilling:Player,val playerKilled:Player,val actionAfterClick:(Lord)->Unit): CustomFragment<Player>(){

    private lateinit var recyclerViewLord: HorizontalRecyclerView
    private lateinit var adapterLord: ImageAdapter<Lord>
    private lateinit var textExplanationLord:TextView

    override val idLayoutToInflate= R.layout.frag_layout_assassin


    override fun createView(viewInflated: View) {

        recyclerViewLord=viewInflated.findViewById(R.id.recyclerViewLordAssassin)
        textExplanationLord=viewInflated.findViewById(R.id.textViewExplanationAssassin)
        textExplanationLord.text="${playerKilling.nom} is using Assassin\n${playerKilled.nom} choose a Lord to kill"

        recyclerViewLord.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        adapterLord= object : ImageAdapter<Lord>(playerKilled.listLord,activity!!,0.5f,0.15f,recyclerViewLord,
            ::createViewHolderLord){
            override fun onClickItem(position: Int) {
                actionAfterClick(listImg[position])
            }
        }

        recyclerViewLord.adapter=adapterLord
    }

    override fun updateView(dataGame: Player) {
        adapterLord.notifyDataSetChanged()
    }
}