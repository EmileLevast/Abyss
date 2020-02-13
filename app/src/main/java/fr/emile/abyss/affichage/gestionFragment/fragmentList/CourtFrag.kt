package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.controller

import fr.emile.abyss.modelClass.gameItems.Court
import fr.emile.abyss.modelClass.gameItems.Lord

//TODO the propriety court is only used to initiate and i don't release it after that
class CourtFrag(private val court:Court?) :CustomFragment<Court>(){
    override val idLayoutToInflate= R.layout.frag_layout_court

    lateinit var lordAdapter:SimpleAdapter
    lateinit var listViewLord:ListView
    lateinit var listLordCourt:List<Lord>

    private val columnName: String
        get() = "name_lord"

    private val columnsId=arrayOf(columnName)

    override fun createView(viewInflated: View) {
        listViewLord=viewInflated.findViewById(R.id.listViewCourt)
        listViewLord.setOnItemClickListener { _, _, position, _ -> controller!!.playerWantToBuyLord(listLordCourt[position]) }
        updateView(court!!)
    }

    override fun updateView(dataGame: Court) {
        listLordCourt=dataGame.listProposedLord
        val listLordName=listLordCourt.map{ mapOf(columnName to it.name)}


        lordAdapter=SimpleAdapter(context,listLordName,R.layout.listview_layout_court,
            columnsId,intArrayOf(R.id.textViewLordCourt))

        listViewLord.adapter=lordAdapter
    }
}