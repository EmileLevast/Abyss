package fr.emile.abyss.gestionFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class CustomFragment : Fragment(){

    //when you override a customFragment give it a layout
    abstract val idLayoutToInflate:Int

    //called in on createView
    //instantiate some view here if you have to do so (typeface, programmatically UI)
    abstract fun createView(viewInflated: View)

    //call in onActivityCreated
    //initiate data thanks to activity
    abstract fun activityCreated()


    //just get the layout to inflate, create the view
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInflated = inflater.inflate(idLayoutToInflate, container, false)
        createView(viewInflated)
        return viewInflated
    }

    //nothing important
    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        activityCreated()
        super.onActivityCreated(savedInstanceState)
    }


}