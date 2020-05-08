package fr.emile.abyss.affichage.gestionFragment.fragmentList

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

import fr.emile.abyss.modelClass.LocationStack
import fr.emile.abyss.modelClass.gameItems.Location
import fr.emile.abyss.modelClass.gameItems.Lord

//TODO the propriety court is only used to initiate and i don't release it after that
class LocationFrag(private val locationStack: LocationStack) :CustomFragment<LocationStack>(){

    private lateinit var buttonDrawLocation1: Button
    private lateinit var buttonDrawLocation2: Button
    private lateinit var buttonDrawLocation3: Button
    private lateinit var buttonDrawLocation4: Button

    lateinit var recyclerViewAvailableLocation: HorizontalRecyclerView
    private lateinit var adapterLocation: ImageAdapter<Location>

    override val idLayoutToInflate= R.layout.frag_layout_location


    override fun createView(viewInflated: View) {

        buttonDrawLocation1=viewInflated.findViewById(R.id.buttonfragLocationDraw1)
        buttonDrawLocation2=viewInflated.findViewById(R.id.buttonfragLocationDraw2)
        buttonDrawLocation3=viewInflated.findViewById(R.id.buttonfragLocationDraw3)
        buttonDrawLocation4=viewInflated.findViewById(R.id.buttonfragLocationDraw4)

        recyclerViewAvailableLocation=viewInflated.findViewById(R.id.recyclerViewFragLocation)



        recyclerViewAvailableLocation.layoutManager= LinearLayoutManager(activity, RecyclerView.VERTICAL,false)

        adapterLocation= object : ImageAdapter<Location>(locationStack.listAvailableLocation,activity!!,0.25f,1f,recyclerViewAvailableLocation,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {

            }
        }

        recyclerViewAvailableLocation.adapter=adapterLocation
    }

    override fun updateView(dataGame: LocationStack) {
        adapterLocation.notifyDataSetChanged()
    }
}