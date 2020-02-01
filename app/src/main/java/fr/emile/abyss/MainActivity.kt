package fr.emile.abyss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.emile.abyss.gestionFragment.GeneratorFragment
import fr.emile.abyss.modelClass.Exploration

var controller:Controller?=null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {

        //si y a pas de generateur de fragment cree
        if (generatorFragment==null)
        generatorFragment=GeneratorFragment(this)

        //we launch the game through the controller creation
        if(controller==null)
            controller=Controller(this)

        super.onResume()

    }

    companion object {
        var generatorFragment:GeneratorFragment?=null
    }
}
