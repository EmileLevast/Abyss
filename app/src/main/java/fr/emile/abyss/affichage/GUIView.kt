package fr.emile.abyss.affichage

import android.widget.Button
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.fragmentList.*
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.EndGame
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.gameItems.Council
import fr.emile.abyss.modelClass.gameItems.Court

class GUIView( activity: MainActivity) {

    var stuffPlayerFrag:StuffPlayerFrag?=null
    var explorationFrag:ExplorationFrag?=null


    init {

        //button
        //Exploration
        val explorationButton:Button=activity.findViewById(R.id.explorationButton)
        explorationButton.setOnClickListener {
            controller!!.launchExploration()
        }

        //council
        val councilButton:Button=activity.findViewById(R.id.councilButton)
        councilButton.setOnClickListener {
            controller!!.launchCouncil()
        }

        //court
        val courtButton:Button=activity.findViewById(R.id.courtButton)
        courtButton.setOnClickListener {
            controller!!.LaunchCourt()
        }
    }



    fun createPlayerScreen(player: Player) {

        stuffPlayerFrag= StuffPlayerFrag(player)

        MainActivity.generatorFragment!!.addFragToActivity(stuffPlayerFrag!!)
    }

    fun updatePlayerScreen(player: Player) {
        stuffPlayerFrag?.updateView(player)
    }

    fun updateExploration(exploration: Exploration) {
        explorationFrag?.updateView(exploration)
        stuffPlayerFrag?.updateView(exploration.listPlayer.getCurrent())
    }

    fun createExploration(exploration: Exploration)
    {
        //FragmentGeneartor should be instantiate on OnResume
        //we create layout params to give a weight to the fragment (when there is many framgents in this container



        explorationFrag= ExplorationFrag(exploration)

        MainActivity.generatorFragment!!.addFragToActivity(explorationFrag!!)

        //we create also a frag to show player stuff
        createPlayerScreen(exploration.listPlayer.getCurrent())
    }


    /**
     * Council
     * **/
    fun createCouncil(council: Council)
    {
        val councilFrag= CouncilFrag(council)

        MainActivity.generatorFragment!!.addFragToActivity(councilFrag)
    }

    /**
     *Court
     */
    fun createCourt(court: Court)
    {
        val courtFrag=CourtFrag(court)
        MainActivity.generatorFragment!!.addFragToActivity(courtFrag)
    }

    /**
     * End game
     */
    fun createEndGameScreen(endGame: EndGame)
    {
        val endGameFrag=EndGameFrag(endGame)
        MainActivity.generatorFragment!!.addFragToActivity(endGameFrag)
    }

    /**remove all the fragment, back to home screen**/
    fun clearScreen()
    {
        MainActivity.generatorFragment!!.popAll()
    }
}
