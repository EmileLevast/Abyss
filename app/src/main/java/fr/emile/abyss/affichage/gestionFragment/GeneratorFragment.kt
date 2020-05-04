package fr.emile.abyss.affichage.gestionFragment

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.WIDTH_SCREEN
import fr.emile.abyss.affichage.gestionFragment.fragmentList.fragmentLordPower.PowerLordFrag


class GeneratorFragment(val mainActivity: MainActivity):FragmentManager.OnBackStackChangedListener{


    private var tagLastFrag:Int=0

    //This contains all the frag to show but not already added to the screen
    //they are waiting for the screen to be clear
    private val poolWaitingFrag= mutableListOf<CustomFragment<*>>()

    init {
        //we add a listener when something apperas in the backstack
        mainActivity.supportFragmentManager.addOnBackStackChangedListener(this)
    }

    fun addFragToActivity(frag:CustomFragment<*>):String
    {
        tagLastFrag++

        val ft=mainActivity.supportFragmentManager.beginTransaction()

        ft.add(R.id.fragmentContainer,frag)//tagLastFrag.toString())
        ft.addToBackStack(tagLastFrag.toString())
        ft.commit()
        setSizeAllFragment()

        return tagLastFrag.toString()
    }

    fun addFragWaitingToBeShown(frag:CustomFragment<*>)
    {
        //s'il y a la place d'afficher les frags
        if(mainActivity.supportFragmentManager.fragments.size<=0)
        {
            //alors on ajoute le frag tout de suite à l'écran
            //il n'y a pas de risques qu'il se fasse delete par un futur clear screen
            addFragToActivity(frag)
        }
        else
        {
            //sinon on l'ajoute à la suite des frags qu'on doit encore afficher à l'écran
            poolWaitingFrag.add(frag)
        }
    }

    fun popAll()
    {
        mainActivity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun popLast()
    {
        mainActivity.supportFragmentManager.popBackStack(tagLastFrag.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    //when the backstack is chnaging
    override fun onBackStackChanged() {
        //on reagerde si l'écran est libre, on voit s'il y a encore des frags en attente d'être affiché
        if(mainActivity.supportFragmentManager.fragments.size<=0 && !poolWaitingFrag.isEmpty())
        {

            //alors on affiche le suivant et on l'enleve de la pool
            addFragToActivity(poolWaitingFrag.removeAt(0))
        }

    }



    private fun setSizeAllFragment()
    {
        val containerLayout=mainActivity.findViewById<LinearLayout>(R.id.fragmentContainer)
        containerLayout.post {

            val preferredWidthFrag = WIDTH_SCREEN!! / containerLayout.childCount

            var currentHeight: Int
            var child: View
            for (i in 0 until containerLayout.childCount) {
                child = containerLayout.getChildAt(i)
                currentHeight = child.layoutParams.height
                child.layoutParams = LinearLayout.LayoutParams(preferredWidthFrag, currentHeight)
            }
        }
    }
}
