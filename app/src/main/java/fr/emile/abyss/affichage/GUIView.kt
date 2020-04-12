package fr.emile.abyss.affichage

import android.widget.Button
import android.widget.RelativeLayout
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.fragmentList.*
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.EndGame
import fr.emile.abyss.modelClass.Exploration
import fr.emile.abyss.modelClass.Player
import fr.emile.abyss.modelClass.Council
import fr.emile.abyss.modelClass.Court
import fr.emile.abyss.modelClass.gameItems.FishType


var WIDTH_SCREEN:Int? = null
var HEIGHT_SCREEN:Int? = null

class GUIView( activity: MainActivity) {

    var stuffPlayerFrag:StuffPlayerFrag?=null
    var explorationFrag:ExplorationFrag?=null
    var councilFrag:CouncilFrag?=null


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

        //we use a view to call on post and get screen size
        courtButton.post { saveSizeScreen(activity) }

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
    fun createCouncil(
        council: Council,
        whatToDoWithCouncil: ((FishType) -> Unit)?=null
    )
    {
        //if there is no action, we don't give the parameter and the councilFrag will take the default action
        councilFrag = if(whatToDoWithCouncil!=null) {
            CouncilFrag(council,whatToDoWithCouncil)
        }else {
            CouncilFrag(council)
        }

        MainActivity.generatorFragment!!.addFragToActivity(councilFrag!!)
    }

    /**
     *Court
     */
    fun createCourt(court: Court)
    {
        val courtFrag=CourtFrag(court)
        MainActivity.generatorFragment!!.addFragToActivity(courtFrag)
        //we create also a frag to show player stuff
        createPlayerScreen(controller!!.game.listPlayer.getCurrent())
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

    /**called once at the launching to register the size of the screen in [WIDTH_SCREEN] et [HEIGHT_SCREEN] **/
    fun saveSizeScreen(activity: MainActivity)
    {
        WIDTH_SCREEN=activity.findViewById<RelativeLayout>(R.id.ecran).width
        HEIGHT_SCREEN=activity.findViewById<RelativeLayout>(R.id.ecran).height
    }


}
