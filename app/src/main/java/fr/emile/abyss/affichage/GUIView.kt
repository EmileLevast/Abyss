package fr.emile.abyss.affichage

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.affichage.gestionFragment.adapter.ImageAdapter
import fr.emile.abyss.affichage.gestionFragment.adapter.ViewHolder
import fr.emile.abyss.affichage.gestionFragment.fragmentList.*
import fr.emile.abyss.affichage.gestionFragment.fragmentList.fragmentLordPower.PowerLordFrag
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.*
import fr.emile.abyss.modelClass.gameItems.FishType
import fr.emile.abyss.modelClass.gameItems.Lord


var WIDTH_SCREEN:Int? = null
var HEIGHT_SCREEN:Int? = null

class GUIView( activity: MainActivity) {

    var stuffPlayerFrag:StuffPlayerFrag?=null
    var explorationFrag:ExplorationFrag?=null
    var councilFrag:CouncilFrag?=null
    var locationFrag:LocationFrag?=null


    var nextTurnLayout:FrameLayout=activity.findViewById(R.id.nextTurnLayout)
    var nextTurnButton:Button=activity.findViewById(R.id.nextTurnButton)

    private var actionBar:ActionBar?=null

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

        //get the action bar
        actionBar=activity.supportActionBar!!

        
        nextTurnLayout.setOnTouchListener{_,_->true}

        //we use a view to call on post and get screen size
        courtButton.post { saveSizeScreen(activity) }

    }

    /**Game Configuration**/
    fun redirectMainMenuToConfigGameFrag()
    {
        printCentralButtonWithtext("Click Here \nto Create a Game")
        nextTurnButton.setOnClickListener {
            controller!!.resetConfigGame()
            addFragToActivity(ConfigGameFrag())
        }
    }

    fun newGameBegin()
    {
        nextTurnLayout.visibility=View.GONE
        initActionOnClickNexTurnButton()
    }

    /**Game organisation**/
    fun initActionOnClickNexTurnButton()
    {
        nextTurnButton.setOnClickListener {
            newTurnBegan()
            controller!!.playerClickToBeginNewTurn()
        }
    }

    //Invocator Power
    fun initActionOnClickAdditionnalTurn()
    {
        nextTurnButton.setOnClickListener {
            newTurnBegan()
            controller!!.playerClickToDoAdditionnalTurn()
            initActionOnClickNexTurnButton()
        }
    }

    fun printCentralButtonWithtext(textToPrint:String)
    {
        nextTurnButton.text=textToPrint
        nextTurnLayout.visibility=View.VISIBLE
    }

    //change visibility of the button to go to next turn
    fun AuthorizeNextTurn(nameNextPlayer: String)
    {
        printCentralButtonWithtext("Finish turn : $nameNextPlayer")
    }

    fun newTurnBegan()
    {
        nextTurnLayout.visibility=View.GONE
    }

    fun updateActionBar(player: Player)
    {
        actionBar!!.title=player.nom
        actionBar!!.setIcon(player.imgId)
    }


    fun <T>addFragToActivity(frag:CustomFragment<T>):String
    {
        //return the tag of the added Frag, if you want to save it
        return MainActivity.generatorFragment!!.addFragToActivity(frag)
    }


    /**Exploration**/
    fun createPlayerScreen(player: Player) {

        stuffPlayerFrag= StuffPlayerFrag(player)

        addFragToActivity(stuffPlayerFrag!!)
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

        addFragToActivity(explorationFrag!!)

        //we create also a frag to show player stuff
        //we set false to disenabled power on Lord Click (because the xploration show player frag who it's not their turn
        stuffPlayerFrag= StuffPlayerFrag(exploration.listPlayer.getCurrent(),false)
        addFragToActivity(stuffPlayerFrag!!)
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

       addFragToActivity(councilFrag!!)
    }

    /**
     *Court
     */
    fun createCourt(court: Court,actionOnClick:(Lord)->Unit= controller!!::playerWantToBuyLord)
    {
        val courtFrag=CourtFrag(court,actionOnClick)
        addFragToActivity(courtFrag)
        //we create also a frag to show player stuff
        createPlayerScreen(controller!!.game.listPlayer.getCurrent())
    }

    /**
     *Location
     */
    fun createLocationStackFrag(locationStack: LocationStack)
    {
        locationFrag=LocationFrag(locationStack)
        MainActivity.generatorFragment!!.addFragWaitingToBeShown(locationFrag!!)
    }

    fun showNewAvailableLocation(locationStack: LocationStack)
    {
        locationFrag!!.showTheseLocationOnly(locationStack.listJustDrawnLocation)
    }


    /**
     * Power lord frag
     */
    fun <T:IShowImage>createPowerLordFrag(listToShow: List<T>,
                                          explicationPower:String,
                                          resourceIdBackground:Int,
                                          factoryViewHolder:(parent: ViewGroup, activity: Context,
                                                          reqHeight: Int, reqWidth: Int,
                                                          onclick: ImageAdapter<T>)-> ViewHolder<T>,
                                          actionAfterOnClick:(T)->Unit={_->Unit},
                                          actionOnClick:(listItem:List<T>,indexClicked:Int)->Unit=
                                              {listItem,indexClicked->actionAfterOnClick(listItem[indexClicked])})
    {
        val powerLordFrag=PowerLordFrag(listToShow,explicationPower,resourceIdBackground,factoryViewHolder,actionOnClick)

        //use a special method to add a PowerLordFrag because we don't want to stack them at the screen
        //and there are some issues, for example they can be deleted with other frags if they are added too early in the backstack
        MainActivity.generatorFragment!!.addFragWaitingToBeShown(powerLordFrag)
    }

    /**
     * End game
     */
    fun createEndGameScreen(endGame: EndGame)
    {
        val endGameFrag=EndGameFrag(endGame)
        MainActivity.generatorFragment!!.addFragWaitingToBeShown(endGameFrag)
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
