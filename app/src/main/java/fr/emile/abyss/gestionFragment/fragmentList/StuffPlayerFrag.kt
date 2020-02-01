package fr.emile.abyss.gestionFragment.fragmentList

import android.view.View
import android.widget.TextView
import fr.emile.abyss.R
import fr.emile.abyss.gestionFragment.CustomFragment
import fr.emile.abyss.modelClass.Player

class StuffPlayerFrag(var player: Player) : CustomFragment() {

    //indicate the layout that we use to show this fragment
    override val idLayoutToInflate: Int= R.layout.frag_layout_player

    override fun createView(viewInflated: View) {
        val textview=viewInflated.findViewById<TextView>(R.id.namePlayer)
        textview.text = player.nom
    }

    override fun activityCreated() {
    }
}