package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.HEIGHT_SCREEN
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.WIDTH_SCREEN
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.*
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.Ally
import fr.emile.abyss.modelClass.gameItems.Deck
import fr.emile.abyss.modelClass.gameItems.Lord

const val RATIO_X_ALLY=0.4f
const val RATIO_Y_ALLY=0.1f
const val RATIO_X_RECYCLER_VIEW=0.5f

class StuffPlayerFrag(val player:Player) : CustomFragment<Player>() {

    //indicate the layout that we use to show this fragment
    override val idLayoutToInflate: Int= R.layout.frag_layout_player

    lateinit var textviewName:TextView
    lateinit var textPerl:TextView
    lateinit var textFederated:TextView
    lateinit var recyclerViewAlly:HorizontalRecyclerView
    lateinit var recyclerViewLord:HorizontalRecyclerView
    lateinit var recyclerViewFederatedAlly:HorizontalRecyclerView
    private lateinit var adapterAlly:ImageAdapter<Ally>
    private lateinit var adapterLord:ImageAdapter<Lord>
    private lateinit var adapterFederatedAlly:ImageAdapter<Ally>



    override fun createView(viewInflated: View) {
        textviewName=viewInflated.findViewById(R.id.namePlayer)
        textPerl=viewInflated.findViewById(R.id.textPerlPlayer)
        textFederated=viewInflated.findViewById(R.id.textViewFederatedPlayer)
        recyclerViewAlly=viewInflated.findViewById(R.id.recyclerViewAlliePlayer)
        recyclerViewLord=viewInflated.findViewById(R.id.recyclerViewLordPlayer)
        recyclerViewFederatedAlly=viewInflated.findViewById(R.id.recyclerViewFederatedAllyPlayer)

        recyclerViewAlly.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewLord.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewFederatedAlly.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        updateView(player)
    }

    /**
     * this function is call to show an other player but on the same frag
     * so a new adapter must be created each time for this new player
     */
    override fun updateView(dataGame: Player) {

        textviewName.text = dataGame.nom
        textPerl.text=("Perl:"+dataGame.perl.toString())
        textFederated.visibility=if(dataGame.listAllieFedere.isEmpty()){View.GONE} else {View.VISIBLE}

        adapterAlly= object : ImageAdapter<Ally>(dataGame.listAlly,activity!!,0.4f,0.1f,recyclerViewAlly, ::createViewHolderAlly){
            override fun onClickItem(position: Int) {
                listImg[position].selectedToBuyLord=!listImg[position].selectedToBuyLord
                notifyDataSetChanged()
            }
        }

        adapterFederatedAlly= object : ImageAdapter<Ally>(dataGame.listAllieFedere,activity!!,0.4f,0.1f,recyclerViewFederatedAlly, ::createViewHolderAlly){
            override fun onClickItem(position: Int) {}
        }

        adapterLord= object : ImageAdapter<Lord>(dataGame.listLord,activity!!,0.4f,0.1f,recyclerViewLord,
            ::createViewHolderLord){
            override fun onClickItem(position: Int) {
                Log.w("msg", "pos:$position")
            }
        }

        recyclerViewAlly.adapter=adapterAlly
        recyclerViewLord.adapter=adapterLord
        recyclerViewFederatedAlly.adapter=adapterFederatedAlly

    }

}