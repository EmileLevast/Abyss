package fr.emile.abyss.affichage.gestionFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * the type parameter given to [CustomFragment] is the type needed for the parameter in [updateView] function
 */
abstract class  CustomFragment<T> : Fragment(){

    /**
    when you override a customFragment give it a [idLayoutToInflate]
     */
    abstract val idLayoutToInflate:Int

    /**called in on createView
     *instantiate some view here if you have to do so (typeface, programmatically UI)
     * initiate values in view
     */
    abstract fun createView(viewInflated: View)

    abstract fun updateView(dataGame:T)

    //abstract fun updateView()

    //just get the layout to inflate, create the view
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInflated = inflater.inflate(idLayoutToInflate, container, false)
        createView(viewInflated)
        return viewInflated
    }

    //nothing important
    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}