package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller

import fr.emile.abyss.modelClass.Court
import fr.emile.abyss.modelClass.gameItems.Lord

//TODO the propriety court is only used to initiate and i don't release it after that
class CourtFrag(private val court: Court?,private val actionOnClick:(Lord)->Unit) :CustomFragment<Court>(){

    private lateinit var buttonPayDrawNewLord: Button

    lateinit var recyclerViewLord: HorizontalRecyclerView
    private lateinit var adapterLord: ImageAdapter<Lord>

    override val idLayoutToInflate= R.layout.frag_layout_court


    override fun createView(viewInflated: View) {
        buttonPayDrawNewLord=viewInflated.findViewById(R.id.buttonPayForANewLordCourt)
        recyclerViewLord=viewInflated.findViewById(R.id.recyclerViewLordCourt)

        buttonPayDrawNewLord.setOnClickListener {
            controller!!.playerPayToDrawNewLord()
            adapterLord.notifyDataSetChanged()
        }

        recyclerViewLord.layoutManager= LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)

        adapterLord= object : ImageAdapter<Lord>(court!!.listProposedLord,activity!!,0.5f,0.15f,recyclerViewLord,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                actionOnClick(listImg[position])
            }
        }

        recyclerViewLord.adapter=adapterLord
    }

    override fun updateView(dataGame: Court) {
        adapterLord.notifyDataSetChanged()
    }
}