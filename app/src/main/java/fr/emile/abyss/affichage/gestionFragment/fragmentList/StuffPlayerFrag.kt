package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.*
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.ActivePermanentPower
import fr.emile.abyss.modelClass.gameItems.Ally
import fr.emile.abyss.modelClass.gameItems.Location
import fr.emile.abyss.modelClass.gameItems.Lord

const val RATIO_X_ALLY=0.4f
const val RATIO_Y_ALLY=0.1f
const val RATIO_X_RECYCLER_VIEW=0.5f

class StuffPlayerFrag(val player:Player, val isPowerLordEnabled:Boolean=true) : CustomFragment<Player>() {

    //indicate the layout that we use to show this fragment
    override val idLayoutToInflate: Int= R.layout.frag_layout_player

    lateinit var textviewName:TextView
    lateinit var textPerl:TextView
    lateinit var textFederated:TextView
    lateinit var recyclerViewAlly:HorizontalRecyclerView
    lateinit var recyclerViewLord:HorizontalRecyclerView
    lateinit var recyclerViewFederatedAlly:HorizontalRecyclerView
    lateinit var recyclerViewLocation:HorizontalRecyclerView
    private lateinit var adapterAlly:ImageAdapter<Ally>
    private lateinit var adapterLord:ImageAdapter<Lord>
    private lateinit var adapterFederatedAlly:ImageAdapter<Ally>
    private lateinit var adapterLocation:ImageAdapter<Location>

    private lateinit var imageViewBackground:ImageView



    override fun createView(viewInflated: View) {
        textviewName=viewInflated.findViewById(R.id.namePlayer)
        textPerl=viewInflated.findViewById(R.id.textPerlPlayer)
        textFederated=viewInflated.findViewById(R.id.textViewFederatedPlayer)
        recyclerViewAlly=viewInflated.findViewById(R.id.recyclerViewAlliePlayer)
        recyclerViewLord=viewInflated.findViewById(R.id.recyclerViewLordPlayer)
        recyclerViewFederatedAlly=viewInflated.findViewById(R.id.recyclerViewFederatedAllyPlayer)
        recyclerViewLocation=viewInflated.findViewById(R.id.recyclerViewLocationPlayer)
        imageViewBackground=viewInflated.findViewById(R.id.imageViewFragPlayerBackground)

        recyclerViewAlly.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewLord.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewFederatedAlly.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewLocation.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        updateView(player)
    }

    /**
     * this function is call to show an other player but on the same frag
     * so a new adapter must be created each time for this new player
     */
    override fun updateView(dataGame: Player) {

        //we init the selected ally
        dataGame.listAlly.forEach { it.selectedToBuyLord=false }

        imageViewBackground.setBackgroundResource(dataGame.imgId)

        textviewName.text = dataGame.nom
        textPerl.text=("Perl:"+dataGame.perl.toString())
        textFederated.visibility=if(dataGame.listAllieFedere.isEmpty()){View.GONE} else {View.VISIBLE}

        adapterAlly= object : ImageAdapter<Ally>(dataGame.listAlly,activity!!,0.4f,0.1f,recyclerViewAlly, ::createViewHolderAllyCheckBox){
            override fun onClickItem(position: Int) {
                listImg[position].selectedToBuyLord=!listImg[position].selectedToBuyLord
                notifyDataSetChanged()
            }
        }

        adapterFederatedAlly= object : ImageAdapter<Ally>(dataGame.listAllieFedere,activity!!,0.4f,0.1f,recyclerViewFederatedAlly, ::createViewHolderAlly){
            override fun onClickItem(position: Int) {}
        }

        adapterLord= object : ImageAdapter<Lord>(dataGame.listLord,activity!!,0.4f,0.1f,recyclerViewLord,
            ::createViewHolderLordWithStatus){
            override fun onClickItem(position: Int) {
                val lord=listImg[position]
                //si le seigneur dispose d'un pouvoir actif
                //et que le frag autorise le declenechement de ces pouvoirs
                //et que le pouvoir n'a pas deja ete utilisé ce tour ci
                if(isPowerLordEnabled && lord.power is ActivePermanentPower && lord.isFree && lord.power.isAvailable )
                {
                    controller!!.view.clearScreen()

                    //alors on l'execute
                    lord.power.activate(dataGame,controller!!.game)


                    //on rend le pouvoir inutilisable pour ce tour ci
                    lord.power.isAvailable=false
                }
            }
        }

        adapterLocation= object : ImageAdapter<Location>(dataGame.listLocation,activity!!,0.4f,0.45f,recyclerViewLocation, ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {}
        }

        recyclerViewAlly.adapter=adapterAlly
        recyclerViewLord.adapter=adapterLord
        recyclerViewFederatedAlly.adapter=adapterFederatedAlly
        recyclerViewLocation.adapter=adapterLocation

    }

}