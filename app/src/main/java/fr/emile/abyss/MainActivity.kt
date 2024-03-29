package fr.emile.abyss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.emile.abyss.affichage.gestionFragment.GeneratorFragment

var controller:Controller?=null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.app_toolbar))
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
