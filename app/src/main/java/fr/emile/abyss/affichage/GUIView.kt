package fr.emile.abyss.affichage

import android.widget.Button
import android.widget.LinearLayout
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.controller
import fr.emile.abyss.gestionFragment.fragmentList.StuffPlayerFrag
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Game

class GUIView( activity: MainActivity) :IView{

    init {
        //instantiate all the view

        //button
        val explorationButton:Button=activity.findViewById(R.id.explorationButton)

        explorationButton.setOnClickListener {
            controller!!.playerLaunchExploration()
        }
    }

    override fun showGame(game: Game) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateExploration(exploration: Exploration) {
    }

    override fun createExploration(exploration: Exploration)
    {
        //FragmentGeneartor should be instantiate on OnResume
        //we create layout params to give a weight to the fragment (when there is many framgents in this container
        val stuffPlayerFrag=StuffPlayerFrag(exploration.listPlayer.getCurrent())

        MainActivity.generatorFragment!!.addFragToActivity(stuffPlayerFrag)
    }
}
