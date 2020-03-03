package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.TextView
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.modelClass.Player


class StuffPlayerFrag(val player:Player) : CustomFragment<Player>() {

    //indicate the layout that we use to show this fragment
    override val idLayoutToInflate: Int= R.layout.frag_layout_player

    lateinit var textviewName:TextView
    lateinit var textNbrAlly:TextView
    lateinit var textNbrLord:TextView
    lateinit var textPerl:TextView


    /**
     * must give a player to this function, to update the view with the caracs of this player
     */
    override fun updateView(dataGame: Player) {
        textviewName.text = dataGame.nom
        textNbrLord.text=("Lord:"+dataGame.listLord.size.toString())
        textPerl.text=("Perl:"+dataGame.perl.toString())
        textNbrAlly.text=("Ally:"+dataGame.listAlly.size.toString())
    }

    override fun createView(viewInflated: View) {
        textviewName=viewInflated.findViewById(R.id.namePlayer)
        textNbrAlly=viewInflated.findViewById(R.id.textNbrAlliePlayer)
        textPerl=viewInflated.findViewById(R.id.textPerlPlayer)
        textNbrLord=viewInflated.findViewById(R.id.textNbrLordPlayer)

        updateView(player)
    }
}