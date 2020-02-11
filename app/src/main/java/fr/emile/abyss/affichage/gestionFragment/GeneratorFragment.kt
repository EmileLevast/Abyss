package fr.emile.abyss.affichage.gestionFragment

import androidx.fragment.app.FragmentManager
import fr.emile.abyss.MainActivity
import fr.emile.abyss.R


class GeneratorFragment(val mainActivity: MainActivity){

    var lastIdentifier=-1

    fun <T>addFragToActivity(frag:CustomFragment<T>)
    {
        //tag_last_frag++

        val ft=mainActivity.supportFragmentManager.beginTransaction()

        ft.add(R.id.fragmentContainer,frag)//tag_last_frag.toString())
        ft.addToBackStack(null)
        lastIdentifier=ft.commit()
    }

    fun popAll()
    {
        mainActivity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
