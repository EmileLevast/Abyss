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

        setButtonVisibility(View.VISIBLE)

        setButtonListener(buttonDrawLocation1,1)
        setButtonListener(buttonDrawLocation2,2)
        setButtonListener(buttonDrawLocation3,3)
        setButtonListener(buttonDrawLocation4,4)

        recyclerViewAvailableLocation=viewInflated.findViewById(R.id.recyclerViewFragLocation)



        recyclerViewAvailableLocation.layoutManager= LinearLayoutManager(activity, RecyclerView.VERTICAL,false)

        adapterLocation= object : ImageAdapter<Location>(locationStack.listAvailableLocation,activity!!,0.25f,1f,recyclerViewAvailableLocation,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                controller!!.playerBuyLocation(listImg[position])
            }
        }

        recyclerViewAvailableLocation.adapter=adapterLocation
    }

    fun showTheseLocationOnly(specificLocationToShow: MutableList<Location>)
    {
        adapterLocation= object : ImageAdapter<Location>(specificLocationToShow,activity!!,0.25f,1f,recyclerViewAvailableLocation,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                controller!!.playerBuyLocation(listImg[position])
            }
        }

        recyclerViewAvailableLocation.adapter=adapterLocation
    }

    override fun updateView(dataGame: LocationStack) {
        adapterLocation.notifyDataSetChanged()
    }

    fun setButtonListener(button:Button,drawNbr:Int)
    {
        button.setOnClickListener {
            setButtonVisibility(View.INVISIBLE)
            controller!!.playerDrawOtherLocations(drawNbr)
        }
    }

    fun setButtonVisibility(visibility:Int)
    {
        buttonDrawLocation1.visibility=visibility
        buttonDrawLocation2.visibility=visibility
        buttonDrawLocation3.visibility=visibility
        buttonDrawLocation4.visibility=visibility
    }

}