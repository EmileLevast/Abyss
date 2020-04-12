package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Council
import fr.emile.abyss.modelClass.gameItems.FishType

class CouncilFrag(val council: Council,val actionOnClick:(fish:FishType)->Unit= CouncilFrag.Companion::defaultActionOnClick) :CustomFragment<Council>(){

    override val idLayoutToInflate: Int= R.layout.frag_layout_council


    lateinit var recyclerViewStackAlly: HorizontalRecyclerView
    private lateinit var adapterFishType: ImageAdapter<FishType>

    override fun createView(viewInflated: View) {


        recyclerViewStackAlly=viewInflated.findViewById(R.id.recyclerViewStackCouncil)

        recyclerViewStackAlly.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        updateView(council)

    }

    override fun updateView(dataGame: Council) {

        //becareful,here .filtervalues make a copy of the list
        //so you must redefine your adapter each time you update
        val listStackAlly:List<FishType> = dataGame.decksAllie.filterValues{!it.isEmpty()}.keys.toList()

        adapterFishType= object : ImageAdapter<FishType>(listStackAlly,activity!!,0.6f,0.2f,recyclerViewStackAlly,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                actionOnClick(listImg[position])
            }
        }

        recyclerViewStackAlly.adapter=adapterFishType
    }

    companion object {
        //l'action par defaut lors du clic sur une image , c'est de prendre une pile du conseil
        fun defaultActionOnClick(fishType: FishType):Unit{
            controller!!.takeCouncilStack(fishType)
        }
    }

}