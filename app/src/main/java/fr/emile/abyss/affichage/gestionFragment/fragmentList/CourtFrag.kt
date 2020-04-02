package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.Ally

import fr.emile.abyss.modelClass.gameItems.Court
import fr.emile.abyss.modelClass.gameItems.Lord

//TODO the propriety court is only used to initiate and i don't release it after that
class CourtFrag(private val court:Court?) :CustomFragment<Court>(){

    lateinit var recyclerViewLord: HorizontalRecyclerView
    private lateinit var adapterLord: ImageAdapter<Lord>

    override val idLayoutToInflate= R.layout.frag_layout_court


    override fun createView(viewInflated: View) {
        recyclerViewLord=viewInflated.findViewById(R.id.recyclerViewLordCourt)

        recyclerViewLord.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        adapterLord= object : ImageAdapter<Lord>(court!!.listProposedLord,activity!!,0.5f,0.15f,recyclerViewLord,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                Log.w("msg", "pos:$position")
            }
        }

        recyclerViewLord.adapter=adapterLord

        updateView(court!!)
    }

    override fun updateView(dataGame: Court) {
        adapterLord.notifyDataSetChanged()
    }
}