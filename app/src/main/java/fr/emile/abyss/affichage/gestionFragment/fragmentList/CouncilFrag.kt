package fr.emile.abyss.affichage.gestionFragment.fragmentList

import android.view.View
import android.widget.Button
import fr.emile.abyss.R
import fr.emile.abyss.affichage.gestionFragment.CustomFragment
import fr.emile.abyss.controller
import fr.emile.abyss.modelClass.gameItems.Council
import fr.emile.abyss.modelClass.gameItems.FishType

class CouncilFrag(val council: Council) :CustomFragment<Council>(){

    override val idLayoutToInflate: Int= R.layout.frag_layout_council

    lateinit var buttonCrab: Button
    lateinit var buttonJellyfish: Button
    lateinit var buttonSeaShell: Button
    lateinit var buttonSeaHorse: Button
    lateinit var buttonOctopus: Button

    override fun createView(viewInflated: View) {
        buttonCrab=viewInflated.findViewById(R.id.textCouncilCrab)
        buttonJellyfish=viewInflated.findViewById(R.id.textCouncilJellyFish)
        buttonOctopus=viewInflated.findViewById(R.id.textCouncilOctopus)
        buttonSeaHorse=viewInflated.findViewById(R.id.textCouncilSeaHorse)
        buttonSeaShell=viewInflated.findViewById(R.id.textCouncilSeaShell)

        buttonCrab.initButtonAction(FishType.CRAB)
        buttonJellyfish.initButtonAction(FishType.JELLYFISH)
        buttonOctopus.initButtonAction(FishType.OCTOPUS)
        buttonSeaShell.initButtonAction(FishType.SEA_SHELL)
        buttonSeaHorse.initButtonAction(FishType.SEA_HORSE)

        updateView(council)

    }

    override fun updateView(dataGame: Council) {
        buttonCrab.updateButtonText(FishType.CRAB,dataGame)
        buttonJellyfish.updateButtonText(FishType.JELLYFISH,dataGame)
        buttonOctopus.updateButtonText(FishType.OCTOPUS,dataGame)
        buttonSeaShell.updateButtonText(FishType.SEA_SHELL,dataGame)
        buttonSeaHorse.updateButtonText(FishType.SEA_HORSE,dataGame)
    }

    private fun Button.initButtonAction(fishType: FishType)
    {
        this.setOnClickListener { controller?.takeCouncilStack(fishType) }
    }

    private fun Button.updateButtonText(fishType: FishType, council: Council)
    {
        val nbrOfCardStacked:Int?=council.decksAllie[fishType]?.size
        this.text=(fishType.toString()+" =  "+nbrOfCardStacked.toString())
    }
}