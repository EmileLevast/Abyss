package fr.emile.abyss.gestionFragment

import fr.emile.abyss.MainActivity
import fr.emile.abyss.R


class GeneratorFragment(val mainActivity: MainActivity){

    init {
        tag_last_frag=0
    }

    fun <T>addFragToActivity(frag:CustomFragment<T>)
    {
        tag_last_frag++

        val ft=mainActivity.supportFragmentManager.beginTransaction()

        ft.add(R.id.fragmentContainer,frag, tag_last_frag.toString())
        ft.addToBackStack(null)
        ft.commit()
    }

    companion object {
        var tag_last_frag:Int=0
    }
}
