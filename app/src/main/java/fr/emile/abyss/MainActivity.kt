package fr.emile.abyss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.emile.abyss.modelClass.Exploration

var controller=Controller()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        

    }
}
