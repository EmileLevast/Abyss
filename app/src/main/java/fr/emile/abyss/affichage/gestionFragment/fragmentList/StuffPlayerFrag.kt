package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.HEIGHT_SCREEN
import fr.emile.abyss.affichage.WIDTH_SCREEN
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.Deck

const val RATIO_X_ALLY=0.4f
const val RATIO_Y_ALLY=0.1f
const val RATIO_X_RECYCLER_VIEW=0.5f

class StuffPlayerFrag(val player:Player) : CustomFragment<Player>() {

    //indicate the layout that we use to show this fragment
    override val idLayoutToInflate: Int= R.layout.frag_layout_player

    lateinit var textviewName:TextView
    lateinit var textNbrAlly:TextView
    private lateinit var textNbrLord:TextView
    lateinit var textPerl:TextView
    lateinit var recyclerViewAlly:HorizontalRecyclerView
    private lateinit var adapterAlly:ImageAdapter



    override fun createView(viewInflated: View) {
        textviewName=viewInflated.findViewById(R.id.namePlayer)
        //textNbrAlly=viewInflated.findViewById(R.id.textNbrAlliePlayer)
        textPerl=viewInflated.findViewById(R.id.textPerlPlayer)
        //textNbrLord=viewInflated.findViewById(R.id.textNbrLordPlayer)
        recyclerViewAlly=viewInflated.findViewById(R.id.recyclerViewAlliePlayer)

        recyclerViewAlly.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        updateView(player)
    }

    /**
     * must give a player to this function, to update the view with the caracs of this player
     */
    override fun updateView(dataGame: Player) {
        textviewName.text = dataGame.nom
        //textNbrLord.text=("Lord:"+dataGame.listLord.size.toString())
        textPerl.text=("Perl:"+dataGame.perl.toString())

        //adapterAlly= ImageAdapter(dataGame.listAlly,activity!!,0.4f,0.1f)
        //TODO change [Deck().stackAlly] by dataGame.listAlly
        adapterAlly= ImageAdapter(Deck().stackAlly,activity!!,0.4f,0.1f)

        recyclerViewAlly.adapter=adapterAlly
        //recyclerViewAlly.layoutParams=LinearLayout.LayoutParams(WIDTH_SCREEN!!/2,recyclerViewAlly.layoutParams.height)

        //textNbrAlly.text=("Ally:"+dataGame.listAlly.size.toString())
    }

}