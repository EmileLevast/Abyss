package fr.emile.abyss.affichage.gestionFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.emile.abyss.R

/**
 * the type parameter given to [CustomFragment] is the type needed for the parameter in [updateView] function
 */
abstract class  CustomFragment<T> : Fragment(){

    /**
    when you override a customFragment give it a [idLayoutToInflate]
     */
    abstract val idLayoutToInflate:Int
    //represent the id of root view that contains the other view in the layout
    //I use it to prevent click on the screen behind the frag
    open val idRootView:Int=R.id.rootView
    //I give a default value to the root id, so you just has to give this id to the root view
    // in your layout file

    /**called in onCreateView()
     *instantiate some view here if you have to do so (typeface, programmatically UI)
     * initiate values in view
     */
    abstract fun createView(viewInflated: View)

    abstract fun updateView(dataGame:T)


    //just get the layout to inflate, create the view
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInflated = inflater.inflate(idLayoutToInflate, container, false)

        /*prepare the background of the fragment*/
        val rootView:View=viewInflated.findViewById(idRootView)
        //we prevent more click on the screen behind
        rootView.setOnTouchListener{_,_-> true}
        //we set a background to see a fragis launched
        rootView.setBackgroundResource(R.color.backgroundFragment)


        createView(viewInflated)
        return viewInflated
    }

    //nothing important
    final override fun onActivityCreated(savedInstanceState: Bundle?) {




        super.onActivityCreated(savedInstanceState)
    }


}