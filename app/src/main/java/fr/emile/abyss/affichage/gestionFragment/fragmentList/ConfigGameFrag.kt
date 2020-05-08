package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import fr.emile.abyss.R
import fr.emile.abyss.affichage.IShowImage
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderImageOnly
import fr.emile.abyss.affichage.gestionFragment.adapter.createViewHolderPlayer
import fr.emile.abyss.affichage.gestionFragment.recyclerView.HorizontalRecyclerView
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.ConfigGame

import fr.emile.abyss.modelClass.Player

//I put Int because we need nothing for an update
//TODO here the generic type Int is useless
class ConfigGameFrag :CustomFragment<Int>(){

    private lateinit var editTextNamePlayer:EditText

    lateinit var recyclerViewPlayerImage: HorizontalRecyclerView
    private lateinit var adapterPlayerImage: ImageAdapter<IShowImage>

    lateinit var recyclerViewPlayerAdded: HorizontalRecyclerView
    private lateinit var adapterPlayerAdded: ImageAdapter<Player>

    override val idLayoutToInflate= R.layout.frag_layout_config_game

    override fun createView(viewInflated: View) {
        editTextNamePlayer=viewInflated.findViewById(R.id.editTextConfigLayoutNewPlayerName)
        recyclerViewPlayerImage=viewInflated.findViewById(R.id.recyclerViewConfigLayoutChooseImagePlayer)
        recyclerViewPlayerAdded=viewInflated.findViewById(R.id.recyclerViewConfigLayoutAllPlayers)

        recyclerViewPlayerImage.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewPlayerAdded.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        adapterPlayerImage= object : ImageAdapter<IShowImage>(
            ConfigGame.listImagePlayer,activity!!,0.4f,0.2f,recyclerViewPlayerImage,
            ::createViewHolderImageOnly){
            override fun onClickItem(position: Int) {
                controller!!.createAPlayer(editTextNamePlayer.text.toString(),listImg[position].imgId)
                updateAdapters()
            }
        }

        adapterPlayerAdded = object : ImageAdapter<Player>(
            controller!!.configGame.listPlayersToAddToGame,activity!!,0.5f,0.15f,recyclerViewPlayerAdded,
            ::createViewHolderPlayer){
            override fun onClickItem(position: Int) {
                controller!!.createTheGame()}
        }

        recyclerViewPlayerImage.adapter=adapterPlayerImage
        recyclerViewPlayerAdded.adapter=adapterPlayerAdded
    }

    override fun updateView(dataGame: Int) {
        updateAdapters()
    }

    fun updateAdapters()
    {
        editTextNamePlayer.clearComposingText()
        adapterPlayerAdded.notifyDataSetChanged()
    }
}
