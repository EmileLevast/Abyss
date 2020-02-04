package fr.emile.abyss.affichage

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.controller
import fr.emile.abyss.gestionFragment.CustomFragment
import fr.emile.abyss.gestionFragment.fragmentList.ExplorationFrag
import fr.emile.abyss.gestionFragment.fragmentList.StuffPlayerFrag
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game
import fr.emile.abyss.modelClass.Player
import kotlin.coroutines.coroutineContext

class GUIView( activity: MainActivity) :IView{

    var stuffPlayerFrag:StuffPlayerFrag?=null
    var explorationFrag:ExplorationFrag?=null


    init {
        //instantiate all the view

        //button
        val explorationButton:Button=activity.findViewById(R.id.explorationButton)

        explorationButton.setOnClickListener {
            controller!!.playerLaunchExploration()
        }
    }

    override fun showGame(game: Game) {
    }

    override fun createPlayerScreen(player: Player) {
        if(stuffPlayerFrag==null)
        {
            stuffPlayerFrag= StuffPlayerFrag(player)
        }

        MainActivity.generatorFragment!!.addFragToActivity(stuffPlayerFrag!!)
    }

    override fun updatePlayerScreen(player: Player) {
        stuffPlayerFrag?.updateView(player)
    }

    override fun updateExploration(exploration: Exploration) {
        explorationFrag?.updateView(exploration)
        stuffPlayerFrag?.updateView(exploration.listPlayer.getCurrent())
    }

    override fun createExploration(exploration: Exploration)
    {
        //FragmentGeneartor should be instantiate on OnResume
        //we create layout params to give a weight to the fragment (when there is many framgents in this container

        if(explorationFrag==null)
        {
            explorationFrag= ExplorationFrag(exploration)
        }

        MainActivity.generatorFragment!!.addFragToActivity(explorationFrag!!)

        //we create also a frag to show player stuff
        createPlayerScreen(exploration.listPlayer.getCurrent())
    }
}
