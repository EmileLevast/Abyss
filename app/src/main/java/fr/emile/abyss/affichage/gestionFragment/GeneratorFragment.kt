package fr.emile.abyss.affichage.gestionFragment

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R
import fr.emile.abyss.affichage.WIDTH_SCREEN


class GeneratorFragment(val mainActivity: MainActivity){

    var lastIdentifier=-1

    fun <T>addFragToActivity(frag:CustomFragment<T>)
    {
        //tag_last_frag++

        val ft=mainActivity.supportFragmentManager.beginTransaction()

        ft.add(R.id.fragmentContainer,frag)//tag_last_frag.toString())
        ft.addToBackStack(null)
        lastIdentifier=ft.commit()
        setSizeAllFragment()
    }

    fun popAll()
    {
        mainActivity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun setSizeAllFragment()
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
