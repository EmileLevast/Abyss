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
    lateinit var recyclerViewAlly:HorizontalRecyclerView
    lateinit var recyclerViewLord:HorizontalRecyclerView
    private lateinit var adapterAlly:ImageAdapter<Ally>
    private lateinit var adapterLord:ImageAdapter<Lord>



    override fun createView(viewInflated: View) {
        textviewName=viewInflated.findViewById(R.id.namePlayer)
        textPerl=viewInflated.findViewById(R.id.textPerlPlayer)
        recyclerViewAlly=viewInflated.findViewById(R.id.recyclerViewAlliePlayer)
        recyclerViewLord=viewInflated.findViewById(R.id.recyclerViewLordPlayer)

        recyclerViewAlly.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewLord.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        updateView(player)
    }

    /**
     * must give a player to this function, to update the view with the caracs of this player
     */
    override fun updateView(dataGame: Player) {
        textviewName.text = dataGame.nom
        textPerl.text=("Perl:"+dataGame.perl.toString())

        adapterAlly= object : ImageAdapter<Ally>(dataGame.listAlly,activity!!,0.4f,0.1f,recyclerViewAlly, ::createViewHolderAlly){
            override fun onClickItem(position: Int) {
                Log.w("msg", "pos:$position")
            }
        }

        adapterLord= object : ImageAdapter<Lord>(dataGame.listLord,activity!!,0.4f,0.1f,recyclerViewLord,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                Log.w("msg", "pos:$position")
            }
        }



        recyclerViewAlly.adapter=adapterAlly
        recyclerViewLord.adapter=adapterLord

    }

}